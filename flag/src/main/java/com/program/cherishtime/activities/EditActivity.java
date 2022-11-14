package com.program.cherishtime.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.UserInfo;
import com.program.cherishtime.data.ObservableUserInfoDb;
import com.program.cherishtime.network.DesertApiClient;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;
import com.program.cherishtime.utils.StatusBarUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class EditActivity extends BaseActivity {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @BindView(R.id.edit_portrait_img)
    CircleImageView editPortraitImg;
    @BindView(R.id.edit_portrait_layout)
    RelativeLayout editPortraitLayout;

    @BindView(R.id.edit_nickname_text)
    TextView editNicknameText;
    @BindView(R.id.edit_nickname_layout)
    RelativeLayout editNicknameLayout;

    @BindView(R.id.edit_account_text)
    TextView editAccountText;
    @BindView(R.id.edit_account_layout)
    RelativeLayout editAccountLayout;

    @BindView(R.id.edit_sex_text)
    TextView editSexText;
    @BindView(R.id.edit_sex_layout)
    RelativeLayout editSexLayout;

    @BindView(R.id.edit_birthday_text)
    TextView editBirthdayText;
    @BindView(R.id.edit_birthday_layout)
    RelativeLayout editBirthdayLayout;

    @BindView(R.id.edit_email_text)
    TextView editEmailText;
    @BindView(R.id.edit_email_layout)
    RelativeLayout editEmailLayout;

    @BindView(R.id.edit_phone_text)
    TextView editPhoneText;
    @BindView(R.id.edit_phone_layout)
    RelativeLayout editPhoneLayout;

    private String date;
    private String sex;
    private String account;

    private File cameraSavePath;
    private Uri mUri;

    private ObservableUserInfoDb mUserInfoDb;

    private List<Disposable> mDisposables = new ArrayList<>();

    private boolean isAllowPermission = false;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, EditActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        StatusBarUtil.setStatusBarDarkTheme(this, false);

        mUserInfoDb = new ObservableUserInfoDb(this);

        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        account = sp.getString(DataUtil.USER_ACCOUNT, "null");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Disposable disposable = mUserInfoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindData);

        fetchUpdates();
        mDisposables.add(disposable);

        LogUtil.d("EditActivity", "onResume");
    }

    private void fetchUpdates() {
        LogUtil.d("EditActivity", account);
        String appKey = MyApplication.getContext().getString(R.string.userInfoDetailsAppKey);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserInfoDetails(appKey, account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserInfoDb::insertUserInfo, this::onFail);
        mDisposables.add(disposable);
    }

    private void bindData(UserInfo userInfo) {
        String url = DesertApiClient.URL + "uploads" + userInfo.getPortrait();
        Glide.with(this).load(url).into(editPortraitImg);

        editNicknameText.setText(userInfo.getNickname());
        editAccountText.setText(userInfo.getAccount());
        if (userInfo.getSex() == 1) {
            editSexText.setText("男");
        } else if (userInfo.getSex() == 2) {
            editSexText.setText("女");
        } else {
            editSexText.setText("保密");
        }

        editBirthdayText.setText(userInfo.getBirthday());
        editEmailText.setText(userInfo.getEmail());
        editPhoneText.setText(userInfo.getPhone());

        date = userInfo.getBirthday();
    }

    private void onFail(Throwable throwable) {
        LogUtil.d("EditActivity", throwable.toString());
    }


    private void init() {
        editPortraitLayout.setOnClickListener(view -> selectPortraitDialog());
        editNicknameLayout.setOnClickListener(view -> editNicknameDialog());
        editSexLayout.setOnClickListener(view -> editSexDialog());

        editAccountLayout.setOnClickListener(view -> Toast.makeText(this,
                editAccountText.getText().toString(), Toast.LENGTH_SHORT).show());

        editBirthdayLayout.setOnClickListener(view -> editBirthdayDialog());
        editEmailLayout.setOnClickListener(view -> editEmailDialog());
        editPhoneLayout.setOnClickListener(view -> editPhoneDialog());
    }

    private void selectPortraitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_title);
        editTitle.setText("头像选择");

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_ok);
        editOk.setOnClickListener(v -> Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show());

        LinearLayout editPortrait = (LinearLayout) view.findViewById(R.id.edit_portrait);
        LinearLayout takePhotoView = (LinearLayout) view.findViewById(R.id.edit_portrait_take_photo);
        LinearLayout choosePhotoView = (LinearLayout) view.findViewById(R.id.edit_portrait_photo);
        LinearLayout randomView = (LinearLayout) view.findViewById(R.id.edit_portrait_random);

        editPortrait.setVisibility(View.VISIBLE);

        takePhotoView.setOnClickListener(v -> {
            if (!isAllowPermission) {
                EditActivityPermissionsDispatcher.NeedsMethodWithPermissionCheck(this);
            } else {
                goCamera();
            }
            dialog.dismiss();
        });

        choosePhotoView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0x001);
            dialog.dismiss();
        });

        randomView.setOnClickListener(v -> Toast.makeText(this, "随机", Toast.LENGTH_SHORT).show());

    }

    private void editNicknameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_title);
        editTitle.setText("修改昵称");

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_ok);

        RelativeLayout editNickName = (RelativeLayout) view.findViewById(R.id.edit_nickname);
        editNickName.setVisibility(View.VISIBLE);

        EditText editNicknameET = (EditText) view.findViewById(R.id.edit_nickname_et);
        ImageView editClear = (ImageView) view.findViewById(R.id.edit_nickname_clear);

        editClear.setOnClickListener(v -> editNicknameET.setText(""));

        editOk.setOnClickListener(v -> {
//            editNicknameText.setText(editNicknameET.getText().toString());
            updateUserInfo("nickname", editNicknameET.getText().toString());
            dialog.dismiss();
        });
    }

    private void updateUserInfo(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put("appKey", MyApplication.getContext().getString(R.string.updateUserAppKey));
        params.put("account", account);
        params.put(key, value);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .updateUser(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUserSuccess, this::onFail);
        mDisposables.add(disposable);
    }

    private void updateUserSuccess(Msg msg) {
        if (msg.getCode() == 1) {
            //成功
            fetchUpdates();
        } else {
            //失败
            Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void editSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_title);
        editTitle.setText("性别选择");

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_ok);

        LinearLayout editSex = (LinearLayout) view.findViewById(R.id.edit_sex);
        editSex.setVisibility(View.VISIBLE);

        FrameLayout male = (FrameLayout) view.findViewById(R.id.edit_sex_male);
        FrameLayout unknown = (FrameLayout) view.findViewById(R.id.edit_sex_unknown);
        FrameLayout female = (FrameLayout) view.findViewById(R.id.edit_sex_female);

        FrameLayout maleSelected = (FrameLayout) view.findViewById(R.id.edit_sex_male_selected);
        FrameLayout unknownSelected = (FrameLayout) view.findViewById(R.id.edit_sex_unknown_selected);
        FrameLayout femaleSelected = (FrameLayout) view.findViewById(R.id.edit_sex_female_selected);

        LinearLayout maleUnSelected = (LinearLayout) view.findViewById(R.id.edit_sex_male_unselected);
        LinearLayout unknownUnSelected = (LinearLayout) view.findViewById(R.id.edit_sex_unknown_unselected);
        LinearLayout femaleUnSelected = (LinearLayout) view.findViewById(R.id.edit_sex_female_unselected);

        male.setOnClickListener(v -> {
            sex = "男";
            maleUnSelected.setVisibility(View.INVISIBLE);
            maleSelected.setVisibility(View.VISIBLE);

            unknownUnSelected.setVisibility(View.VISIBLE);
            unknownSelected.setVisibility(View.INVISIBLE);
            femaleUnSelected.setVisibility(View.VISIBLE);
            femaleSelected.setVisibility(View.INVISIBLE);
        });
        unknown.setOnClickListener(v -> {
            sex = "保密";
            unknownUnSelected.setVisibility(View.INVISIBLE);
            unknownSelected.setVisibility(View.VISIBLE);

            maleUnSelected.setVisibility(View.VISIBLE);
            maleSelected.setVisibility(View.INVISIBLE);
            femaleUnSelected.setVisibility(View.VISIBLE);
            femaleSelected.setVisibility(View.INVISIBLE);
        });
        female.setOnClickListener(v -> {
            sex = "女";
            femaleUnSelected.setVisibility(View.INVISIBLE);
            femaleSelected.setVisibility(View.VISIBLE);

            unknownUnSelected.setVisibility(View.VISIBLE);
            unknownSelected.setVisibility(View.INVISIBLE);
            maleUnSelected.setVisibility(View.VISIBLE);
            maleSelected.setVisibility(View.INVISIBLE);
        });

        editOk.setOnClickListener(v -> {
            if (sex.equals("男")) {
                updateUserInfo("sex", 1);
            }
            if (sex.equals("女")) {
                updateUserInfo("sex", 2);
            }
            if (sex.equals("保密")) {
                updateUserInfo("sex", 0);
            }
//            editSexText.setText(sex);
            dialog.dismiss();
        });
    }

    private void editBirthdayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_title);
        editTitle.setText("生日选择");

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_ok);

        LinearLayout editBirthday = (LinearLayout) view.findViewById(R.id.edit_birthday);
        editBirthday.setVisibility(View.VISIBLE);

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.edit_birthday_pick);

        datePicker.init(1999, 10, 21, (datePicker1, year, month, day) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            date = format.format(calendar.getTime());
        });

        editOk.setOnClickListener(v -> {
//            editBirthdayText.setText(date);
            if (!date.equals("")) {
                updateUserInfo("birthday", date);
            }
            dialog.dismiss();
        });
    }

    private void editEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_title);
        editTitle.setText("修改邮箱");

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_ok);

        RelativeLayout editEmail = (RelativeLayout) view.findViewById(R.id.edit_email);
        editEmail.setVisibility(View.VISIBLE);

        EditText editEmailET = (EditText) view.findViewById(R.id.edit_email_et);
        ImageView editClear = (ImageView) view.findViewById(R.id.edit_email_clear);

        editClear.setOnClickListener(v -> editEmailET.setText(""));

        editOk.setOnClickListener(v -> {
//            editEmailText.setText(editEmailET.getText().toString());
            if (!editEmailET.getText().toString().equals("")) {
                updateUserInfo("email", editEmailET.getText().toString());
            }
            dialog.dismiss();
        });
    }

    private void editPhoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_title);
        editTitle.setText("修改电话");

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_ok);

        RelativeLayout editPhone = (RelativeLayout) view.findViewById(R.id.edit_phone);
        editPhone.setVisibility(View.VISIBLE);

        EditText editPhoneET = (EditText) view.findViewById(R.id.edit_phone_et);
        ImageView editClear = (ImageView) view.findViewById(R.id.edit_phone_clear);

        editClear.setOnClickListener(v -> editPhoneET.setText(""));

        editOk.setOnClickListener(v -> {
//            editPhoneText.setText(editPhoneET.getText().toString());
            if (!editPhoneET.getText().toString().equals("")) {
                updateUserInfo("phone", editPhoneET.getText().toString());
            }
            dialog.dismiss();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0x001 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                LogUtil.d("EditActivity", "pic = " + picturePath);

                onloadImage(picturePath);
            }
        }
        if (requestCode == 0x002 && resultCode == RESULT_OK) {
            String photoPath;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = mUri.getEncodedPath();
            }
            LogUtil.d("EditActivity", "photo path = " + photoPath);
            onloadImage(photoPath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onloadImage(String path) {
        File file = null;
        if (path != null) {
            file = new File(path);
        }
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .uploadImage(createFilePart("images", file), createFormBody(account))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> {
                    if (msg.getCode() == 1) {
                        LogUtil.d("EditActivity", "url = " + msg.getMsg());
                        fetchUpdates();
                    } else {
                        Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }, this::onFail);
        mDisposables.add(disposable);
    }

    //创建表单的普通字段
    private RequestBody createFormBody(String content) {
        RequestBody body = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), content);
        return body;
    }

    //创建Multipart, fieldName为表单字段名
    private MultipartBody.Part createFilePart(String fieldName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
        return body;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EditActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void NeedsMethod() {
        isAllowPermission = true;
        goCamera();
    }

    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mUri = FileProvider.getUriForFile(EditActivity.this,
                    "com.program.cherishtime.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            mUri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, 0x002);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    public void RationaleMethod(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("是否授予权限？")
                .setPositiveButton("确定", (dialogInterface, i) -> request.proceed())
                .setNegativeButton("取消", (dialogInterface, i) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void DeniedMethod() {
        Toast.makeText(EditActivity.this, "权限获取失败！", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void AskMethod() {
        new AlertDialog.Builder(this)
                .setTitle("权限设置提醒")
                .setMessage("相机权限被拒绝,为了不影响您的正常使用，请在 设置-权限 中开启对应权限")
                .setPositiveButton("去设置", (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", EditActivity.this.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    Toast.makeText(EditActivity.this, "没有相机权限，拍照功能受限！", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}
