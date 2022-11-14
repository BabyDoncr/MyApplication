package com.program.cherishtime.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Statistic;
import com.program.cherishtime.bean.UserInfo;
import com.program.cherishtime.data.ObservableUserInfoDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartActivity extends BaseActivity {

    @BindView(R.id.chart_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.home_pieChart)
    PieChartView mPieChart;

    @BindView(R.id.line_chart)
    LineChartView lineChart;
    @BindView(R.id.points_text)
    TextView pointsText;
    @BindView(R.id.points)
    TextView points;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    private List<Disposable> mDisposables = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        lineChart.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scrollView.requestDisallowInterceptTouchEvent(false);
            } else {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

        mPieChart.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scrollView.requestDisallowInterceptTouchEvent(false);
            } else {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ObservableUserInfoDb infoDb = new ObservableUserInfoDb(this);
        Disposable disposable = infoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::queryStatistic);
        mDisposables.add(disposable);
    }

    private void queryStatistic(UserInfo userInfo) {
        int uid = userInfo.getId();
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryStatisticById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData, this::onError);

        mDisposables.add(disposable);
    }

    private void onError(Throwable throwable) {
        LogUtil.d("Statistic", throwable.getMessage());
    }

    private void setData(ArrayList<Statistic> statistics) {
        Statistic statistic = statistics.get(0);
        setPieChartData(statistics.get(1), mPieChart);
        Collections.reverse(statistics);
        setChartViewData(statistics, lineChart);

        pointsText.setText("今日得分");
        int p = (statistic.getDone() * 10) > 100 ? 100 : (statistic.getDone() * 10);
        points.setText(String.valueOf(p));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setPieChartData(Statistic statistic, PieChartView pieChart) {
        int numValues = 4;

        String[] label = new String[]{"学习", "休息", "运动", "其它"};

        List<Integer> value = new ArrayList<>();
        value.add(statistic.getStudy());
        value.add(statistic.getRest());
        value.add(statistic.getSports());
        value.add(statistic.getOther());

        List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < numValues; i++) {
            SliceValue sliceValue = new SliceValue(value.get(i), ChartUtils.pickColor());
            sliceValue.setLabel(label[i]);
            values.add(sliceValue);
        }

        PieChartData pieChartData = new PieChartData(values);
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOnlyForSelected(false);
        pieChartData.setHasLabelsOutside(false);
        pieChartData.setHasCenterCircle(true);
        pieChartData.setCenterCircleScale(0.5f);

        String month = statistic.getTime().substring(4, 6);
        String day = statistic.getTime().substring(6, 8);

        pieChartData.setCenterText1(month + "月" + day + "日");
        pieChartData.setCenterText1FontSize(24);
        pieChartData.setCenterText2("共接取：" + statistic.getTotal());

        pieChart.setPieChartData(pieChartData);
        pieChart.setChartRotationEnabled(true);
        pieChart.setChartRotation(180, true);

        pieChart.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, SliceValue sliceValue) {
                Toast.makeText(ChartActivity.this, "Selected: " + label[i] + " " + sliceValue.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });

    }

    private void setChartViewData(List<Statistic> dataList, LineChartView lineChart) {
        List<PointValue> mPointValues1 = new ArrayList<>();
        List<PointValue> mPointValues2 = new ArrayList<>();
        List<PointValue> mPointValues3 = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            mPointValues1.add(new PointValue(i, dataList.get(i).getTotal()));
            mPointValues2.add(new PointValue(i, dataList.get(i).getDone()));
            mPointValues3.add(new PointValue(i, dataList.get(i).getGiveUp()));
            String time = dataList.get(i).getTime();
            axisValues.add(new AxisValue(i).setLabel(time.substring(4)));

            list.add(dataList.get(i).getTotal());
        }

        int dataSize = dataList.size();

        int maxPoint = Collections.max(list);

        Line line = new Line(mPointValues1);
        Line line1 = new Line(mPointValues2);
        Line line2 = new Line(mPointValues3);

        List<Line> lines = new ArrayList<>();

        line.setHasLabels(true);
        line.setShape(ValueShape.DIAMOND);
        line.setPointRadius(3);
        line.setCubic(true);
        line.setFilled(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        line.setColor(Color.parseColor("#FEB04C"));
        line.setStrokeWidth(1);
        lines.add(line);

        line1.setHasLabels(true);
        line1.setShape(ValueShape.DIAMOND);
        line1.setPointRadius(3);
        line1.setCubic(true);
        line1.setFilled(true);
        line1.setHasLines(true);
        line1.setHasPoints(true);
        line1.setColor(Color.parseColor("#85B74F"));
        line1.setStrokeWidth(1);
        lines.add(line1);

        line2.setHasLabels(true);
        line2.setShape(ValueShape.DIAMOND);
        line2.setPointRadius(3);
        line2.setCubic(true);
        line2.setFilled(true);
        line2.setHasLines(true);
        line2.setHasPoints(true);
        line2.setColor(Color.parseColor("#DC143C"));
        line2.setStrokeWidth(1);
        lines.add(line2);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        data.setBaseValue(Float.NEGATIVE_INFINITY);

        Axis axisX = new Axis();
        axisX.setTextSize(10);
        axisX.setTextColor(Color.parseColor("#666666"));
        axisX.setHasLines(false);
        axisX.setValues(axisValues);
        axisX.setHasTiltedLabels(true);

        data.setAxisXBottom(axisX);

        Axis axisY = new Axis();
        axisY.setTextSize(12);
        axisY.setTextColor(Color.parseColor("#666666"));
        axisY.setHasLines(false);
        data.setAxisYLeft(axisY);

        lineChart.setInteractive(true);

        lineChart.setSaveEnabled(true);

        lineChart.setLineChartData(data);

        lineChart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, int i1, PointValue pointValue) {
                LogUtil.d("Statistic", "i = " + i + "  i1 = " + i1);
                LogUtil.d("Statistic", "" + pointValue.getX() + "  " + pointValue.getY());
                Toast.makeText(ChartActivity.this,
                        String.format(Locale.CHINA, "%s - %d", dataList.get(i1).getTime(), dataList.get(i1).getTotal()),
                        Toast.LENGTH_SHORT).show();
                setPieChartData(dataList.get(i1), mPieChart);
                Statistic statistic = dataList.get(i1);
                if (TimeUtil.getYearMonthDay().equals(statistic.getTime())) {
                    pointsText.setText("今日得分");
                } else {
                    String month = statistic.getTime().substring(4, 6);
                    String day = statistic.getTime().substring(6, 8);
                    pointsText.setText(String.format("%s月%s日得分", month, day));
                }
                int p = (statistic.getDone() * 10) > 100 ? 100 : (statistic.getDone() * 10);
                points.setText(String.valueOf(p));
            }

            @Override
            public void onValueDeselected() {

            }
        });

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.top = maxPoint + 3;
        v.bottom = 0;
        lineChart.setMaximumViewport(v);

        v.right = dataSize - 1;

        if (dataSize > 7) {
            v.left = dataSize - 7;
        } else {
            v.left = 0;
        }
        lineChart.setCurrentViewport(v);
    }

    @Override
    protected void onDestroy() {
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
        super.onDestroy();
    }
}
