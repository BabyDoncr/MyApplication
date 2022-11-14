package com.program.cherishtime.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Statistic;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.LogUtil;

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

public class StatisticFragment extends BaseFragment {

    private int uid;

    @BindView(R.id.pie_chart_view)
    PieChartView mPieChart;

    @BindView(R.id.line_chart_view)
    LineChartView lineChart;

    private Disposable mDisposable;

    public StatisticFragment(int uid) {
        this.uid = uid;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.statistic_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDisposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryStatisticById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData, this::onError);
    }

    private void setData(ArrayList<Statistic> statistics) {
        setPieChartData(statistics.get(1), mPieChart);
        Collections.reverse(statistics);
        setChartViewData(statistics, lineChart);
    }

    private void onError(Throwable throwable) {
        LogUtil.d("Statistic", throwable.getMessage());
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
                Toast.makeText(mContext, "Selected: " + label[i] + " " + sliceValue.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });

    }

    private void setChartViewData(List<Statistic> dataList, LineChartView lineChart) {
        List<PointValue> mPointValues1 = new ArrayList<>();
        List<PointValue> mPointValues2 = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            mPointValues1.add(new PointValue(i, dataList.get(i).getTotal()));
            mPointValues2.add(new PointValue(i, dataList.get(i).getDone()));
            String time = dataList.get(i).getTime();
            axisValues.add(new AxisValue(i).setLabel(time.substring(4)));

            list.add(dataList.get(i).getTotal());
        }

        int dataSize = dataList.size();

        int maxPoint = Collections.max(list);

        Line line = new Line(mPointValues1);
        Line line1 = new Line(mPointValues2);

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
                Toast.makeText(mContext,
                        String.format(Locale.CHINA, "%s - %d", dataList.get(i1).getTime(), dataList.get(i1).getTotal()),
                        Toast.LENGTH_SHORT).show();
                setPieChartData(dataList.get(i1), mPieChart);

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
    public void onDestroy() {
        if (mDisposable != null)
            mDisposable.dispose();
        super.onDestroy();
    }
}
