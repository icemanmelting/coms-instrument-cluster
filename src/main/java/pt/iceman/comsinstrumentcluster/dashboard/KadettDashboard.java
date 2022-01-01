package pt.iceman.comsinstrumentcluster.dashboard;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import eu.hansolo.medusa.*;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import pt.iceman.comsinstrumentcluster.screen.AbsolutePositioning;
import pt.iceman.comsinstrumentcluster.screen.CustomEntry;
import pt.iceman.middleware.cars.BaseCommand;
import pt.iceman.middleware.cars.ice.ICEBased;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KadettDashboard extends Dashboard {
    private Map<Double, Image> gearMapping;
    private Map<Integer, Image> temperatureFuelMapping;
    private PerspectiveCamera camera;
    private SubScene subscene;
    private boolean ignition;

    public KadettDashboard() {
        super();
        this.ignition = false;
    }

    private void initCamera() {
        this.camera = new PerspectiveCamera(true);
        this.camera.setFieldOfView(45);
        this.camera.getTransforms().addAll(
                new Rotate(90, new Point3D(-0.2505628070857316, -0.9351131265310294, -0.25056280708573153)),
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Translate(0, -10, 0),
                new Translate(0, 0, -80) //-80
        );

        this.camera.setNearClip(0.1);
        this.camera.setFarClip(200);
    }

    private void initSubscene() {
        Group model = loadModel();
        model.getTransforms().add(new Scale(0.1, 0.1, 0.1));

        this.subscene = new SubScene(model, 1920, 720, true, SceneAntialiasing.BALANCED);
        this.subscene.setCamera(this.camera);
    }

    @Override
    public void configureInstruments() {
        initCamera();
        initSubscene();

        gearMapping = new HashMap<Double, Image>(7) {{
            put(0d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.Neutral.getFieldValue()))));
            put(1d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.First.getFieldValue()))));
            put(2d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.Second.getFieldValue()))));
            put(3d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.Third.getFieldValue()))));
            put(4d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.Forth.getFieldValue()))));
            put(5d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.Fifth.getFieldValue()))));
            put(6d, new Image(Objects.requireNonNull(getClass().getResourceAsStream(Gear.Reverse.getFieldValue()))));
        }};

        temperatureFuelMapping = new HashMap<Integer, Image>(2) {{
            put(1, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temperatureWarning.jpg"))));
            put(2, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temperature.jpg"))));
            put(3, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fuelWarning.jpg"))));
            put(4, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fuel.jpg"))));
        }};

        this.speedGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.DIGITAL)
                .autoScale(false)
                .lcdVisible(true)
                .lcdCrystalEnabled(true)
                .lcdDesign(LcdDesign.BLACK_RED)
                .barColor(Color.BLANCHEDALMOND)
                .barBackgroundColor(Color.BLANCHEDALMOND)
                .foregroundBaseColor(Color.BLANCHEDALMOND)
                .titleColor(Color.BLANCHEDALMOND)
                .valueColor(Color.BLANCHEDALMOND)
                .title("Km/h")
                .decimals(0)
                .scaleDirection(Gauge.ScaleDirection.CLOCKWISE)
                .minValue(0.0D)
                .maxValue(220.0D)
                .majorTickSpace(50D)
                .majorTickMarksVisible(false)
                .mediumTickMarksVisible(false)
                .minorTickMarksVisible(false)
                .angleRange(120D)
                .startFromZero(false)
                .returnToZero(false)
                .build();

        this.speedGauge.setValue(172);

        speedGauge.setAnimationDuration(800);
        speedGauge.toBack();

        TranslateTransition speedTransition = new TranslateTransition();
        speedTransition.setByX(500);
        speedTransition.setDuration(Duration.millis(1000));
        speedTransition.setNode(speedGauge);

        speedGaugeAbsPos = new AbsolutePositioning();
        speedGaugeAbsPos.setPosX(-385);
        speedGaugeAbsPos.setPosY(65);
        speedGaugeAbsPos.setWidth(550);
        speedGaugeAbsPos.setHeight(550);
        speedGaugeAbsPos.setOrder(0);
        speedGaugeAbsPos.setAnimation(speedTransition);

        Section orange = SectionBuilder.create().start(3500.0D).stop(4000.0D).color(Color.ORANGE).build();
        Section red = SectionBuilder.create().start(4001.0D).stop(4500.0D).color(Color.RED).build();

        this.rpmGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.DIGITAL)
                .autoScale(false)
                .barColor(Color.BLANCHEDALMOND)
                .barBackgroundColor(Color.BLANCHEDALMOND)
                .foregroundBaseColor(Color.BLANCHEDALMOND)
                .titleColor(Color.BLANCHEDALMOND)
                .valueColor(Color.BLANCHEDALMOND)
                .title("RPM")
                .decimals(0)
                .scaleDirection(Gauge.ScaleDirection.CLOCKWISE)
                .minValue(0.0D)
                .maxValue(4500.0D)
                .majorTickSpace(1000D)
                .minorTickSpace(500D)
                .majorTickMarksVisible(false)
                .mediumTickMarksVisible(false)
                .minorTickMarksVisible(false)
                .startFromZero(false)
                .returnToZero(false)
                .sections(orange, red)
                .sectionsVisible(true)
                .build();

        this.rpmGauge.setValue(3478);

        rpmGaugeAbsPos = new AbsolutePositioning();
        rpmGaugeAbsPos.setPosX(1236);
        rpmGaugeAbsPos.setPosY(65);
        rpmGaugeAbsPos.setWidth(550);
        rpmGaugeAbsPos.setHeight(550);
        rpmGaugeAbsPos.setOrder(1);

        rpmGauge.setAnimationDuration(800);
        rpmGauge.toBack();

        animationAbsPos = new AbsolutePositioning();
        animationAbsPos.setPosX(0);
        animationAbsPos.setPosY(0);
        animationAbsPos.setWidth(500);
        animationAbsPos.setHeight(500);
        animationAbsPos.setOrder(2);
//        animationAbsPos.setAnimation(speedTransition);

//        gearShift = new Image(getClass().getResourceAsStream(Gear.Neutral.getFieldValue()));
//        gearShiftView = new ImageView(gearShift);
//        gearShiftView.toFront();

//        gearShiftAbsPos = new AbsolutePositioning();
//        gearShiftAbsPos.setPosX(355);
//        gearShiftAbsPos.setPosY(5);
//        gearShiftAbsPos.setWidth(100);
//        gearShiftAbsPos.setHeight(78);
//        gearShiftAbsPos.setOrder(1);

        tempGauge = GaugeBuilder.create()
                .sections(new Section(50, 65, Color.BLUE), new Section(100, 130, Color.RED))
                .sectionsVisible(true)
                .title("ºC")
                .minValue(50)
                .maxValue(120)
                .majorTickSpace(20)
                .minorTickSpace(10)
                .startAngle(270)
                .angleRange(180)
                .animationDuration(400)
                .barColor(Color.BLANCHEDALMOND)
                .barBackgroundColor(Color.BLANCHEDALMOND)
                .foregroundBaseColor(Color.BLANCHEDALMOND)
                .titleColor(Color.BLANCHEDALMOND)
                .valueColor(Color.BLANCHEDALMOND)
                .skinType(Gauge.SkinType.DIGITAL)
                .build();

        tempGauge.setValue(60);

        tempGaugeAbsPos = new AbsolutePositioning();
        tempGaugeAbsPos.setPosX(263);
        tempGaugeAbsPos.setPosY(480);
        tempGaugeAbsPos.setWidth(255);
        tempGaugeAbsPos.setHeight(255);
        tempGaugeAbsPos.setOrder(5);

        tempImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temperature.jpg")));
        tempImageView = new ImageView();
        tempImageView.setImage(tempImage);
        tempImageView.toFront();
        tempImageAbsPos = new AbsolutePositioning();
        tempImageAbsPos.setPosX(378);
        tempImageAbsPos.setPosY(660);
        tempImageAbsPos.setWidth(35);
        tempImageAbsPos.setHeight(20);
        tempImageAbsPos.setOrder(6);

        dieselGauge = GaugeBuilder.create()
                .sections(new Section(0, 7, Color.RED))
                .sectionsVisible(true)
                .title("L")
                .minValue(0)
                .maxValue(52)
                .minorTickSpace(1)
                .majorTickSpace(5)
                .animationDuration(400)
                .barColor(Color.BLANCHEDALMOND)
                .barBackgroundColor(Color.BLANCHEDALMOND)
                .foregroundBaseColor(Color.BLANCHEDALMOND)
                .titleColor(Color.BLANCHEDALMOND)
                .valueColor(Color.BLANCHEDALMOND)
                .skinType(Gauge.SkinType.DIGITAL)
                .build();

        dieselGauge.setValue(5.4);

        dieselGaugeAbsPos = new AbsolutePositioning();
        dieselGaugeAbsPos.setPosX(1388);
        dieselGaugeAbsPos.setPosY(480);
        dieselGaugeAbsPos.setWidth(255);
        dieselGaugeAbsPos.setHeight(255);
        dieselGaugeAbsPos.setOrder(7);

        dieselImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fuel.jpg")));
        dieselImageView = new ImageView();
        dieselImageView.setImage(dieselImage);
        dieselImageView.toFront();

        dieselImageAbsPos = new AbsolutePositioning();
        dieselImageAbsPos.setPosX(1508);
        dieselImageAbsPos.setPosY(660);
        dieselImageAbsPos.setWidth(35);
        dieselImageAbsPos.setHeight(20);
        dieselImageAbsPos.setOrder(8);

        totalDistanceLcd = GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.LCD)
                .lcdDesign(LcdDesign.BLACK_RED)
                .lcdCrystalEnabled(false)
                .unit("Km")
                .decimals(1)
                .minValue(0)
                .maxValue(99999)
                .animated(true)
                .areaIconsVisible(false)
                .innerShadowEnabled(false)
                .oldValueVisible(false)
                .maxMeasuredValueVisible(false)
                .minMeasuredValueVisible(false)
                .build();

        totalDistanceLcd.setValue(23500);

        totalDistanceLcdAbsPos = new AbsolutePositioning();
        totalDistanceLcdAbsPos.setPosX(290);
        totalDistanceLcdAbsPos.setPosY(380);
        totalDistanceLcdAbsPos.setWidth(200);
        totalDistanceLcdAbsPos.setHeight(100);
        totalDistanceLcdAbsPos.setOrder(1);

        brakesOilImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/brakesWarning.jpg")));
        brakesOilImageView = new ImageView();
        brakesOilImageView.setVisible(false);
        brakesOilImageView.setImage(brakesOilImage);
        brakesOilImageView.toFront();
        brakesOilImageAbsPos = new AbsolutePositioning();
        brakesOilImageAbsPos.setPosX(600);
        brakesOilImageAbsPos.setPosY(0);
        brakesOilImageAbsPos.setWidth(26);
        brakesOilImageAbsPos.setHeight(25);
        brakesOilImageAbsPos.setOrder(10);

        oilPressureImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/oilPressure.jpg")));
        oilPressureImageView = new ImageView();
        oilPressureImageView.setVisible(false);
        oilPressureImageView.setImage(oilPressureImage);
        oilPressureImageView.toFront();
        oilPressureImageAbsPos = new AbsolutePositioning();
        oilPressureImageAbsPos.setPosX(687);
        oilPressureImageAbsPos.setPosY(0);
        oilPressureImageAbsPos.setWidth(26);
        oilPressureImageAbsPos.setHeight(25);
        oilPressureImageAbsPos.setOrder(9);

        batteryImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/battery.jpg")));
        batteryImageView = new ImageView();
        batteryImageView.setVisible(false);
        batteryImageView.setImage(batteryImage);
        batteryImageView.toFront();
        batteryImageAbsPos = new AbsolutePositioning();
        batteryImageAbsPos.setPosX(774);
        batteryImageAbsPos.setPosY(0);
        batteryImageAbsPos.setWidth(26);
        batteryImageAbsPos.setHeight(25);
        batteryImageAbsPos.setOrder(11);

        parkingImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/parking.jpg")));
        parkingImageView = new ImageView();
        parkingImageView.setVisible(false);
        parkingImageView.setImage(parkingImage);
        parkingImageView.toFront();
        parkingImageAbsPos = new AbsolutePositioning();
        parkingImageAbsPos.setPosX(868);
        parkingImageAbsPos.setPosY(0);
        parkingImageAbsPos.setWidth(26);
        parkingImageAbsPos.setHeight(25);
        parkingImageAbsPos.setOrder(12);

        highBeamsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/highBeams.jpg")));
        highBeamsImageView = new ImageView();
        highBeamsImageView.setVisible(false);
        highBeamsImageView.setImage(highBeamsImage);
        highBeamsImageView.toFront();
        highBeamsImageAbsPos = new AbsolutePositioning();
        highBeamsImageAbsPos.setPosX(955);
        highBeamsImageAbsPos.setPosY(0);
        highBeamsImageAbsPos.setWidth(26);
        highBeamsImageAbsPos.setHeight(25);
        highBeamsImageAbsPos.setOrder(13);

        absImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/abs.jpg")));
        absImageView = new ImageView();
        absImageView.setVisible(false);
        absImageView.setImage(absImage);
        absImageView.toFront();
        absImageAbsPos = new AbsolutePositioning();
        absImageAbsPos.setPosX(1042);
        absImageAbsPos.setPosY(0);
        absImageAbsPos.setWidth(26);
        absImageAbsPos.setHeight(25);
        absImageAbsPos.setOrder(14);

        sparkPlugImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sparkPlug.jpg")));
        sparkPlugImageView = new ImageView();
        sparkPlugImageView.setVisible(false);
        sparkPlugImageView.setImage(sparkPlugImage);
        sparkPlugImageView.toFront();
        sparkPlugImageAbsPos = new AbsolutePositioning();
        sparkPlugImageAbsPos.setPosX(1129);
        sparkPlugImageAbsPos.setPosY(0);
        sparkPlugImageAbsPos.setWidth(26);
        sparkPlugImageAbsPos.setHeight(25);
        sparkPlugImageAbsPos.setOrder(15);

        turningSignsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/turnSigns.jpg")));
        turningSignsImageView = new ImageView();
        turningSignsImageView.setVisible(false);
        turningSignsImageView.setImage(turningSignsImage);
        turningSignsImageView.toFront();
        turningSignsImageAbsPos = new AbsolutePositioning();
        turningSignsImageAbsPos.setPosX(1216);
        turningSignsImageAbsPos.setPosY(0);
        turningSignsImageAbsPos.setWidth(26);
        turningSignsImageAbsPos.setHeight(25);
        turningSignsImageAbsPos.setOrder(16);

        iceImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/iceFormation.jpg")));
        iceImageView = new ImageView();
        iceImageView.setVisible(false);
        iceImageView.setImage(iceImage);
        iceImageView.toFront();
        iceImageAbsPos = new AbsolutePositioning();
        iceImageAbsPos.setPosX(1303);
        iceImageAbsPos.setPosY(0);
        iceImageAbsPos.setWidth(26);
        iceImageAbsPos.setHeight(25);
        iceImageAbsPos.setOrder(17);

        getNodes().add(new CustomEntry<>(rpmGauge, rpmGaugeAbsPos));
        getNodes().add(new CustomEntry<>(dieselGauge, dieselGaugeAbsPos));
        getNodes().add(new CustomEntry<>(speedGauge, speedGaugeAbsPos));
        getNodes().add(new CustomEntry<>(subscene, animationAbsPos));
        getNodes().add(new CustomEntry<>(tempGauge, tempGaugeAbsPos));
        getNodes().add(new CustomEntry<>(tempImageView, tempImageAbsPos));
        getNodes().add(new CustomEntry<>(dieselImageView, dieselImageAbsPos));
        getNodes().add(new CustomEntry<>(totalDistanceLcd, totalDistanceLcdAbsPos));
//        getNodes().add(new CustomEntry<>(gearShift, gearShiftAbsPos));

        getNodes().add(new CustomEntry<>(oilPressureImageView, oilPressureImageAbsPos));
        getNodes().add(new CustomEntry<>(brakesOilImageView, brakesOilImageAbsPos));
        getNodes().add(new CustomEntry<>(batteryImageView, batteryImageAbsPos));
        getNodes().add(new CustomEntry<>(parkingImageView, parkingImageAbsPos));
        getNodes().add(new CustomEntry<>(highBeamsImageView, highBeamsImageAbsPos));
        getNodes().add(new CustomEntry<>(absImageView, absImageAbsPos));
        getNodes().add(new CustomEntry<>(sparkPlugImageView, sparkPlugImageAbsPos));
        getNodes().add(new CustomEntry<>(turningSignsImageView, turningSignsImageAbsPos));
        getNodes().add(new CustomEntry<>(iceImageView, iceImageAbsPos));

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                speedGauge.setValue(getSpeed());
                rpmGauge.setValue(rpm);
                distanceLcd.setValue(distance);
                totalDistanceLcd.setValue(totalDistance);
                dieselGauge.setValue(diesel);
                tempGauge.setValue(temp);
//                if ((rpm < 1000 && speed >= 0) || (rpm > 1000 && speed == 0)) {
//                    gear = 0;
//                }
                Platform.runLater(() -> {
                    gearShift = gearMapping.get(gear);
                    gearShiftView.setImage(gearShift);
                });
            }
        };

        animationTimer.start();
    }

    private void animateStart(Camera camera) {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), camera);
        rt.setCycleCount(1);
        rt.setAxis(Rotate.Y_AXIS);
        rt.setByAngle(-93);
        rt.setInterpolator(Interpolator.LINEAR);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), camera);
        tt.setByZ(10);

        rt.setOnFinished(e -> tt.play());

        rt.play();
    }

    private void animateStop(Camera camera) {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), camera);
        rt.setCycleCount(1);
        rt.setAxis(Rotate.Y_AXIS);
        rt.setByAngle(93);
        rt.setInterpolator(Interpolator.LINEAR);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), camera);
        tt.setByZ(-10);

        tt.setOnFinished(e -> rt.play());

        tt.play();
    }

    private Group loadModel() {
        Group modelRoot = new Group();

        TdsModelImporter importer = new TdsModelImporter();
        importer.read(getClass().getClassLoader().getResource("Opel_Kadett_E_Hatchback_3door_1991.3ds"));

        Node[] meshes = importer.getImport();

        for (Node view : meshes) {
            if (!view.getId().equals("LicPlate01_Root") && !view.getId().equals("LicPlate02_Root")) {
                modelRoot.getChildren().add(view);
            }
        }

        return modelRoot;
    }

    @Override
    public void setTemp(double temp) {
        super.setTemp(temp);

        if (temp > 110) {
            Platform.runLater(() -> {
                tempImageView.setImage(temperatureFuelMapping.get(1));
            });
        } else {
            Platform.runLater(() -> {
                tempImageView.setImage(temperatureFuelMapping.get(2));
            });
        }
    }

    @Override
    public void setDiesel(double diesel) {
        super.setDiesel(diesel);

        if (diesel <= 6) {
            Platform.runLater(() -> {
                dieselImageView.setImage(temperatureFuelMapping.get(3));
            });
        } else {
            Platform.runLater(() -> {
                dieselImageView.setImage(temperatureFuelMapping.get(4));
            });
        }
    }

    @Override
    public <T extends BaseCommand> void applyCommand(T baseCommand) {
        super.applyCommand(baseCommand);

        ICEBased iceBased = (ICEBased) baseCommand;
        setOilPressure(iceBased.isOilPressureLow());
        setSparkPlug(iceBased.isSparkPlugOn());
        setRpm(iceBased.getRpm());
        setDiesel(iceBased.getFuelLevel());
        setTemp(iceBased.getEngineTemperature());

        if (state == null) {
            if (ignition) {
                state = new IgnitionOn();
            } else {
                state = new IgnitionOff();
            }
        }

        if (iceBased.isIgnition() && !ignition) {
            state.transitState();
            state = new IgnitionOn();
        } else if (!iceBased.isIgnition() && ignition) {
            state.transitState();
            state = new IgnitionOff();
        }

        ignition = iceBased.isIgnition();
    }

    private class IgnitionOn implements State {
        @Override
        public void transitState() {
            animateStop(camera);
        }
    }

    private class IgnitionOff implements State {
        @Override
        public void transitState() {
            animateStart(camera);
        }
    }
}