package pt.iceman.comsinstrumentcluster.dashboard;

import eu.hansolo.medusa.*;
import javafx.animation.*;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import pt.iceman.comsinstrumentcluster.screen.AbsolutePositioning;
import pt.iceman.comsinstrumentcluster.screen.CustomEntry;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import java.util.HashMap;
import java.util.Map;

public class KadettDashboard extends Dashboard {
    private Map<Double, Image> gearMapping;
    private Map<Integer, Image> temperatureFuelMapping;


    public KadettDashboard() {
        super();
    }

    @Override
    public void configureInstruments() {
//        gearMapping = new HashMap<Double, Image>(7) {{
//            put(0d, new Image(getClass().getResourceAsStream(Gear.Neutral.getFieldValue())));
//            put(1d, new Image(getClass().getResourceAsStream(Gear.First.getFieldValue())));
//            put(2d, new Image(getClass().getResourceAsStream(Gear.Second.getFieldValue())));
//            put(3d, new Image(getClass().getResourceAsStream(Gear.Third.getFieldValue())));
//            put(4d, new Image(getClass().getResourceAsStream(Gear.Forth.getFieldValue())));
//            put(5d, new Image(getClass().getResourceAsStream(Gear.Fifth.getFieldValue())));
//            put(6d, new Image(getClass().getResourceAsStream(Gear.Reverse.getFieldValue())));
//        }};
//
//        temperatureFuelMapping = new HashMap<Integer, Image>(2) {{
//            put(1, new Image(getClass().getResourceAsStream("/temperatureWarning.jpg")));
//            put(2, new Image(getClass().getResourceAsStream("/temperature.jpg")));
//            put(3, new Image(getClass().getResourceAsStream("/fuelWarning.jpg")));
//            put(4, new Image(getClass().getResourceAsStream("/fuel.jpg")));
//        }};

        this.speedGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.DIGITAL)
                .autoScale(false)
                .barColor(Color.CORNFLOWERBLUE)
                .barBackgroundColor(Color.CORNFLOWERBLUE)
                .foregroundBaseColor(Color.CORNFLOWERBLUE)
                .titleColor(Color.CORNFLOWERBLUE)
                .valueColor(Color.CORNFLOWERBLUE)
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

        this.rpmGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.DIGITAL)
                .autoScale(false)
                .barColor(Color.CORNFLOWERBLUE)
                .barBackgroundColor(Color.CORNFLOWERBLUE)
                .foregroundBaseColor(Color.CORNFLOWERBLUE)
                .titleColor(Color.CORNFLOWERBLUE)
                .valueColor(Color.CORNFLOWERBLUE)
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
                .build();

        TranslateTransition rpmTransition = new TranslateTransition();
        rpmTransition.setByX(-684);
        rpmTransition.setDuration(Duration.millis(1000));
        rpmTransition.setNode(rpmGauge);

        rpmGaugeAbsPos = new AbsolutePositioning();
        rpmGaugeAbsPos.setPosX(1920);
        rpmGaugeAbsPos.setPosY(65);
        rpmGaugeAbsPos.setWidth(550);
        rpmGaugeAbsPos.setHeight(550);
        rpmGaugeAbsPos.setOrder(1);
        rpmGaugeAbsPos.setAnimation(rpmTransition);

        rpmGauge.setAnimationDuration(800);
        rpmGauge.toBack();

        animationAbsPos = new AbsolutePositioning();
        animationAbsPos.setPosX(0);
        animationAbsPos.setPosY(0);
        animationAbsPos.setWidth(500);
        animationAbsPos.setHeight(500);
        animationAbsPos.setOrder(2);
        //animationAbsPos.setAnimation(speedTransition);
//
//        gearShift = new Image(getClass().getResourceAsStream(Gear.Neutral.getFieldValue()));
//        gearShiftView = new ImageView(gearShift);
//        gearShiftView.toFront();
//
//        gearShiftAbsPos = new AbsolutePositioning();
//        gearShiftAbsPos.setPosX(355);
//        gearShiftAbsPos.setPosY(5);
//        gearShiftAbsPos.setWidth(100);
//        gearShiftAbsPos.setHeight(78);
//        gearShiftAbsPos.setOrder(1);
//

//
//        tempGauge = GaugeBuilder.create()
//                .sections(new Section(50, 65), new Section(120, 130))
//                .unit("ºC")
//                .minValue(50)
//                .maxValue(130)
//                .majorTickSpace(20)
//                .minorTickSpace(10)
//                .build();
//
//        tempGauge.setStyle(" -section-fill-0: rgb(24.0, 113.0, 214.0);-section-fill-1: rgb(225.0, 0.0, 69.0);");
//        tempGauge.setAnimationDuration(400);
//        tempGauge.setStartAngle(270);
//        tempGauge.setAngleRange(180);
//        tempGauge.toFront();
//
//        TranslateTransition tempTransition = new TranslateTransition();
//        tempTransition.setByX(800);
//        tempTransition.setDuration(Duration.millis(1000));
//        tempTransition.setNode(tempGauge);
//
//        tempGaugeAbsPos = new AbsolutePositioning();
//        tempGaugeAbsPos.setPosX(-700);
//        tempGaugeAbsPos.setPosY(330);
//        tempGaugeAbsPos.setWidth(220);
//        tempGaugeAbsPos.setHeight(220);
//        tempGaugeAbsPos.setOrder(14);
//        tempGaugeAbsPos.setAnimation(tempTransition);
//
//        dieselGauge = GaugeBuilder.create()
//                .sections(new Section(0, 7))
//                .unit("L")
//                .minValue(0)
//                .maxValue(52)
//                .majorTickSpace(10)
//                .minorTickSpace(5)
//                .build();
//
//        dieselGauge.setAnimationDuration(1500);
//        dieselGauge.setStyle("-section-fill-0: rgb(225.0, 0.0, 69.0);");
//        dieselGauge.setStartAngle(270);
//        dieselGauge.setAngleRange(180);
//        dieselGauge.toFront();
//
//        TranslateTransition dieselTransition = new TranslateTransition();
//        dieselTransition.setByX(-800);
//        dieselTransition.setDuration(Duration.millis(1000));
//        dieselTransition.setNode(dieselGauge);
//
//        dieselGaugeAbsPos = new AbsolutePositioning();
//        dieselGaugeAbsPos.setPosX(1290);
//        dieselGaugeAbsPos.setPosY(330);
//        dieselGaugeAbsPos.setWidth(220);
//        dieselGaugeAbsPos.setHeight(220);
//        dieselGaugeAbsPos.setOrder(15);
//        dieselGaugeAbsPos.setAnimation(dieselTransition);
//
//        distanceLcd = LcdBuilder
//                .create()
//                .lcdDesign(Lcd.LcdDesign.WHITE)
//                .foregroundShadowVisible(false)
//                .unit("Km")
//                .unitVisible(true)
//                .decimals(1)
//                .animationDurationInMs(400)
//                .maxMeasuredValueVisible(false)
//                .threshold(26)
//                .minValue(0)
//                .maxValue(99999)
//                .valueFont(Lcd.LcdFont.ELEKTRA)
//                .animated(true)
//                .build();
//
//
//        distanceLcd.setOnMouseClicked(
//                t -> CarCPU.resetDashTripKm()
//        );
//
//        distanceLcdAbsPos = new AbsolutePositioning();
//        distanceLcdAbsPos.setPosX(330);
//        distanceLcdAbsPos.setPosY(380);
//        distanceLcdAbsPos.setWidth(150);
//        distanceLcdAbsPos.setHeight(40);
//        distanceLcdAbsPos.setOrder(1);
//
//        totalDistanceLcd = LcdBuilder
//                .create()
//                .lcdDesign(Lcd.LcdDesign.WHITE)
//                .foregroundShadowVisible(false)
//                .unit("Km").unitVisible(true)
//                .decimals(1)
//                .animationDurationInMs(400).maxMeasuredValueVisible(false)
//                .threshold(26)
//                .minValue(0)
//                .maxValue(99999)
//                .valueFont(Lcd.LcdFont.ELEKTRA)
//                .animated(true)
//                .build();
//
//        totalDistanceLcdAbsPos = new AbsolutePositioning();
//        totalDistanceLcdAbsPos.setPosX(330);
//        totalDistanceLcdAbsPos.setPosY(420);
//        totalDistanceLcdAbsPos.setWidth(150);
//        totalDistanceLcdAbsPos.setHeight(40);
//        totalDistanceLcdAbsPos.setOrder(1);
//
//        tempImage = new Image(getClass().getResourceAsStream("/temperature.jpg"));
//        tempImageView = new ImageView();
//        tempImageView.setImage(tempImage);
//        tempImageView.toFront();
//        tempImageAbsPos = new AbsolutePositioning();
//        tempImageAbsPos.setPosX(195);
//        tempImageAbsPos.setPosY(385);
//        tempImageAbsPos.setWidth(35);
//        tempImageAbsPos.setHeight(20);
//        tempImageAbsPos.setOrder(13);
//
//        dieselImage = new Image(getClass().getResourceAsStream("/fuel.jpg"));
//        dieselImageView = new ImageView();
//        dieselImageView.setImage(dieselImage);
//        dieselImageView.toFront();
//        dieselImageAbsPos = new AbsolutePositioning();
//        dieselImageAbsPos.setPosX(590);
//        dieselImageAbsPos.setPosY(385);
//        dieselImageAbsPos.setWidth(35);
//        dieselImageAbsPos.setHeight(20);
//        dieselImageAbsPos.setOrder(14);
//
//        oilPressureImage = new Image(getClass().getResourceAsStream("/oilPressure.jpg"));
//        oilPressureImageView = new ImageView();
//        oilPressureImageView.setVisible(false);
//        oilPressureImageView.setImage(oilPressureImage);
//        oilPressureImageView.toFront();
//        oilPressureImageAbsPos = new AbsolutePositioning();
//        oilPressureImageAbsPos.setPosX(516);
//        oilPressureImageAbsPos.setPosY(238);
//        oilPressureImageAbsPos.setWidth(26);
//        oilPressureImageAbsPos.setHeight(25);
//        oilPressureImageAbsPos.setOrder(2);
//
//        brakesOilImage = new Image(getClass().getResourceAsStream("/brakesWarning.jpg"));
//        brakesOilImageView = new ImageView();
//        brakesOilImageView.setVisible(false);
//        brakesOilImageView.setImage(brakesOilImage);
//        brakesOilImageView.toFront();
//        brakesOilImageAbsPos = new AbsolutePositioning();
//        brakesOilImageAbsPos.setPosX(193);
//        brakesOilImageAbsPos.setPosY(165);
//        brakesOilImageAbsPos.setWidth(26);
//        brakesOilImageAbsPos.setHeight(25);
//        brakesOilImageAbsPos.setOrder(3);
//
//        batteryImage = new Image(getClass().getResourceAsStream("/battery.jpg"));
//        batteryImageView = new ImageView();
//        batteryImageView.setVisible(false);
//        batteryImageView.setImage(batteryImage);
//        batteryImageView.toFront();
//        batteryImageAbsPos = new AbsolutePositioning();
//        batteryImageAbsPos.setPosX(138);
//        batteryImageAbsPos.setPosY(205);
//        batteryImageAbsPos.setWidth(26);
//        batteryImageAbsPos.setHeight(25);
//        batteryImageAbsPos.setOrder(4);
//
//        parkingImage = new Image(getClass().getResourceAsStream("/parking.jpg"));
//        parkingImageView = new ImageView();
//        parkingImageView.setVisible(false);
//        parkingImageView.setImage(parkingImage);
//        parkingImageView.toFront();
//        parkingImageAbsPos = new AbsolutePositioning();
//        parkingImageAbsPos.setPosX(193);
//        parkingImageAbsPos.setPosY(271);
//        parkingImageAbsPos.setWidth(26);
//        parkingImageAbsPos.setHeight(25);
//        parkingImageAbsPos.setOrder(5);
//
//        highBeamsImage = new Image(getClass().getResourceAsStream("/highBeams.jpg"));
//        highBeamsImageView = new ImageView();
//        highBeamsImageView.setVisible(false);
//        highBeamsImageView.setImage(highBeamsImage);
//        highBeamsImageView.toFront();
//        highBeamsImageAbsPos = new AbsolutePositioning();
//        highBeamsImageAbsPos.setPosX(623);
//        highBeamsImageAbsPos.setPosY(180);
//        highBeamsImageAbsPos.setWidth(26);
//        highBeamsImageAbsPos.setHeight(25);
//        highBeamsImageAbsPos.setOrder(6);
//
//        absImage = new Image(getClass().getResourceAsStream("/abs.jpg"));
//        absImageView = new ImageView();
//        absImageView.setVisible(false);
//        absImageView.setImage(absImage);
//        absImageView.toFront();
//        absImageAbsPos = new AbsolutePositioning();
//        absImageAbsPos.setPosX(250);
//        absImageAbsPos.setPosY(200);
//        absImageAbsPos.setWidth(26);
//        absImageAbsPos.setHeight(25);
//        absImageAbsPos.setOrder(7);
//
//        sparkPlugImage = new Image(getClass().getResourceAsStream("/sparkPlug.jpg"));
//        sparkPlugImageView = new ImageView();
//        sparkPlugImageView.setVisible(false);
//        sparkPlugImageView.setImage(sparkPlugImage);
//        sparkPlugImageView.toFront();
//        sparkPlugImageAbsPos = new AbsolutePositioning();
//        sparkPlugImageAbsPos.setPosX(253);
//        sparkPlugImageAbsPos.setPosY(252);
//        sparkPlugImageAbsPos.setWidth(26);
//        sparkPlugImageAbsPos.setHeight(25);
//        sparkPlugImageAbsPos.setOrder(8);
//
//        turningSignsImage = new Image(getClass().getResourceAsStream("/turnSigns.jpg"));
//        turningSignsImageView = new ImageView();
//        turningSignsImageView.setVisible(false);
//        turningSignsImageView.setImage(turningSignsImage);
//        turningSignsImageView.toFront();
//        turningSignsImageAbsPos = new AbsolutePositioning();
//        turningSignsImageAbsPos.setPosX(638);
//        turningSignsImageAbsPos.setPosY(241);
//        turningSignsImageAbsPos.setWidth(26);
//        turningSignsImageAbsPos.setHeight(25);
//        turningSignsImageAbsPos.setOrder(9);
//
//        iceImage = new Image(getClass().getResourceAsStream("/ice formation.jpg"));
//        iceImageView = new ImageView();
//        iceImageView.setVisible(false);
//        iceImageView.setImage(iceImage);
//        iceImageView.toFront();
//        iceImageAbsPos = new AbsolutePositioning();
//        iceImageAbsPos.setPosX(525);
//        iceImageAbsPos.setPosY(190);
//        iceImageAbsPos.setWidth(26);
//        iceImageAbsPos.setHeight(25);
//        iceImageAbsPos.setOrder(10);
//
//        backgroundImage = new Image(getClass().getResourceAsStream("/background.jpg"));
//        backgroundImageView = new ImageView();
//        backgroundImageView.setImage(backgroundImage);
//        backgroundImageView.toFront();
//        backgroundImageAbsPos = new AbsolutePositioning();
//        backgroundImageAbsPos.setPosX(-1);
//        backgroundImageAbsPos.setPosY(0);
//        backgroundImageAbsPos.setWidth(800);
//        backgroundImageAbsPos.setHeight(480);
//        backgroundImageAbsPos.setOrder(0);
//
        getNodes().add(new CustomEntry<>(rpmGauge, rpmGaugeAbsPos));
//        getNodes().add(new CustomEntry<>(dieselGauge, dieselGaugeAbsPos));
        getNodes().add(new CustomEntry<>(speedGauge, speedGaugeAbsPos));
        getNodes().add(new CustomEntry<>(createSubScene(), animationAbsPos));
//        getNodes().add(new CustomEntry<>(tempGauge, tempGaugeAbsPos));
//        getNodes().add(new CustomEntry<>(tempImageView, tempImageAbsPos));
//        getNodes().add(new CustomEntry<>(dieselImageView, dieselImageAbsPos));
//        getNodes().add(new CustomEntry<>(distanceLcd, distanceLcdAbsPos));
//        getNodes().add(new CustomEntry<>(gearShiftView, gearShiftAbsPos));
//        getNodes().add(new CustomEntry<>(totalDistanceLcd, totalDistanceLcdAbsPos));
//        getNodes().add(new CustomEntry<>(oilPressureImageView, oilPressureImageAbsPos));
//        getNodes().add(new CustomEntry<>(brakesOilImageView, brakesOilImageAbsPos));
//        getNodes().add(new CustomEntry<>(batteryImageView, batteryImageAbsPos));
//        getNodes().add(new CustomEntry<>(parkingImageView, parkingImageAbsPos));
//        getNodes().add(new CustomEntry<>(highBeamsImageView, highBeamsImageAbsPos));
//        getNodes().add(new CustomEntry<>(absImageView, absImageAbsPos));
//        getNodes().add(new CustomEntry<>(sparkPlugImageView, sparkPlugImageAbsPos));
//        getNodes().add(new CustomEntry<>(turningSignsImageView, turningSignsImageAbsPos));
//        getNodes().add(new CustomEntry<>(backgroundImageView, backgroundImageAbsPos));
//        getNodes().add(new CustomEntry<>(iceImageView, iceImageAbsPos));

//        animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                speedGauge.setValue(getSpeed());
//                rpmGauge.setValue(rpm);
//                distanceLcd.setValue(distance);
//                totalDistanceLcd.setValue(totalDistance);
//                dieselGauge.setValue(diesel);
//                tempGauge.setValue(temp);
////                if ((rpm < 1000 && speed >= 0) || (rpm > 1000 && speed == 0)) {
////                    gear = 0;
////                }
//                Platform.runLater(() -> {
//                    gearShift = gearMapping.get(gear);
//                    gearShiftView.setImage(gearShift);
//                });
//            }
//        };
//
//        animationTimer.start();
    }

    private void animate(Camera camera) {
        try {
            RotateTransition rt = new RotateTransition(Duration.seconds(3), camera);
            rt.setCycleCount(Integer.MAX_VALUE);
            rt.setAxis(Rotate.Y_AXIS);
//            rt.setByAngle(-120);
            rt.setByAngle(360);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.play();

//            RotateTransition rt2 = new RotateTransition(Duration.seconds(3));
//            rt2.setAxis(Rotate.Z_AXIS);
//            rt2.setByAngle(-120);
//            rt2.setInterpolator(Interpolator.LINEAR);
//
//            TranslateTransition tt = new TranslateTransition(Duration.seconds(3));
//            tt.setByX(50);
//
//            SequentialTransition sequentialTransition = new SequentialTransition(camera, rt, rt2, tt);
////        sequentialTransition.setAutoReverse(true);
//
//            sequentialTransition.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SubScene createSubScene() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(45);

        camera.getTransforms().addAll(
                new Rotate(-60, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Translate(0, -10, 0),
                new Translate(0, 0, -80) //-80
        );

        camera.setNearClip(0.1);
        camera.setFarClip(200);

        Group model = loadModel();
        model.getTransforms().add(new Scale(0.1, 0.1, 0.1));

        SubScene subScene = new SubScene(model, 1920, 720, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        animate(camera);

        return subScene;
    }

    private Group loadModel() {
        Group modelRoot = new Group();

        TdsModelImporter importer = new TdsModelImporter();
        importer.read(getClass().getClassLoader().getResource("Opel_Kadett_E_Hatchback_3door_1991.3ds"));

        Node[] meshes = importer.getImport();

        for (Node view : meshes) {
            modelRoot.getChildren().add(view);
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
}