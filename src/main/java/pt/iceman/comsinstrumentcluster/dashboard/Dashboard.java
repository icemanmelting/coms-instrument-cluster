package pt.iceman.comsinstrumentcluster.dashboard;

import eu.hansolo.medusa.Gauge;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.iceman.comsinstrumentcluster.screen.AbsolutePositioning;
import pt.iceman.comsinstrumentcluster.screen.Screen;

public abstract class Dashboard extends Screen {
    protected Gauge speedGauge;
    protected AbsolutePositioning speedGaugeAbsPos;
    protected double speed;
    protected Image gearShift;
    protected ImageView gearShiftView;
    protected AbsolutePositioning gearShiftAbsPos;
    protected double gear;
    protected Gauge tempGauge;
    protected AbsolutePositioning tempGaugeAbsPos;
    protected double temp;
    protected Image tempImage;
    protected ImageView tempImageView;
    protected AbsolutePositioning tempImageAbsPos;
    protected Gauge rpmGauge;
    protected AbsolutePositioning rpmGaugeAbsPos;
    protected double rpm;
    protected Gauge dieselGauge;
    protected AbsolutePositioning dieselGaugeAbsPos;
    protected double diesel;
    protected Image dieselImage;
    protected ImageView dieselImageView;
    protected AbsolutePositioning dieselImageAbsPos;
    protected Gauge distanceLcd;
    protected AbsolutePositioning distanceLcdAbsPos;
    protected double distance;
    protected Gauge totalDistanceLcd;
    protected AbsolutePositioning totalDistanceLcdAbsPos;
    protected double totalDistance;
    protected AnimationTimer animationTimer;
    protected boolean brakesOil;
    protected Image brakesOilImage;
    protected ImageView brakesOilImageView;
    protected AbsolutePositioning brakesOilImageAbsPos;
    protected boolean battery;
    protected Image batteryImage;
    protected ImageView batteryImageView;
    protected AbsolutePositioning batteryImageAbsPos;
    protected boolean abs;
    protected Image absImage;
    protected ImageView absImageView;
    protected AbsolutePositioning absImageAbsPos;
    protected boolean parking;
    protected Image parkingImage;
    protected ImageView parkingImageView;
    protected AbsolutePositioning parkingImageAbsPos;
    protected boolean highBeams;
    protected Image highBeamsImage;
    protected ImageView highBeamsImageView;
    protected AbsolutePositioning highBeamsImageAbsPos;
    protected boolean oilPressure;
    protected Image oilPressureImage;
    protected ImageView oilPressureImageView;
    protected AbsolutePositioning oilPressureImageAbsPos;
    protected boolean sparkPlug;
    protected Image sparkPlugImage;
    protected ImageView sparkPlugImageView;
    protected AbsolutePositioning sparkPlugImageAbsPos;
    protected boolean turnSigns;
    protected Image turningSignsImage;
    protected ImageView turningSignsImageView;
    protected AbsolutePositioning turningSignsImageAbsPos;
    protected Image backgroundImage;
    protected ImageView backgroundImageView;
    protected AbsolutePositioning backgroundImageAbsPos;
    protected boolean ice;
    protected Image iceImage;
    protected ImageView iceImageView;
    protected AbsolutePositioning iceImageAbsPos;
    protected AbsolutePositioning animationAbsPos;

    public Dashboard() {
        this.configureInstruments();
    }

    public synchronized Double getSpeed() {
        return this.speed;
    }

    public synchronized void setSpeed(double speed) {
        this.speed = speed;
    }

    public Double getRpm() {
        return this.rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getGear() {
        return this.gear;
    }

    public void setGear(double gear) {
        this.gear = gear;
    }

    public synchronized double getDistance() {
        return this.distance;
    }

    public synchronized double getTotalDistance() {
        return this.totalDistance;
    }

    public synchronized void setDistance(double distance) {
        this.distance = distance;
    }

    public synchronized void resetDistance() {
        this.distance = 0.0D;
    }

    public synchronized void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTemp() {
        return this.temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getDiesel() {
        return this.diesel;
    }

    public void setDiesel(double diesel) {
        this.diesel = diesel;
    }

    public boolean isBrakesOil() {
        return this.brakesOil;
    }

    public void setBrakesOil(boolean brakesOil) {
        this.brakesOil = brakesOil;
        Platform.runLater(() -> {
            this.brakesOilImageView.setVisible(brakesOil);
        });
    }

    public boolean isBattery() {
        return this.battery;
    }

    public void setBattery(boolean battery) {
        this.battery = battery;
        Platform.runLater(() -> {
            this.batteryImageView.setVisible(battery);
        });
    }

    public boolean isAbs() {
        return this.abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
        Platform.runLater(() -> {
            this.absImageView.setVisible(abs);
        });
    }

    public boolean isParking() {
        return this.parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
        Platform.runLater(() -> {
            this.parkingImageView.setVisible(parking);
        });
    }

    public boolean isHighBeams() {
        return this.highBeams;
    }

    public void setHighBeams(boolean highBeams) {
        this.highBeams = highBeams;
        Platform.runLater(() -> {
            this.highBeamsImageView.setVisible(highBeams);
        });
    }

    public boolean isOilPressure() {
        return this.oilPressure;
    }

    public void setOilPressure(boolean oilPressure) {
        this.oilPressure = oilPressure;
        Platform.runLater(() -> {
            this.oilPressureImageView.setVisible(oilPressure);
        });
    }

    public boolean isSparkPlug() {
        return this.sparkPlug;
    }

    public void setSparkPlug(boolean sparkPlug) {
        this.sparkPlug = sparkPlug;
        Platform.runLater(() -> {
            this.sparkPlugImageView.setVisible(sparkPlug);
        });
    }

    public boolean isTurnSigns() {
        return this.turnSigns;
    }

    public void setTurnSigns(boolean turnSigns) {
        this.turnSigns = turnSigns;
        Platform.runLater(() -> {
            this.turningSignsImageView.setVisible(turnSigns);
        });
    }

    public void setIce(boolean ice) {
        this.ice = ice;
        Platform.runLater(() -> {
            this.iceImageView.setVisible(ice);
        });
    }

    public void configureInstruments() {
    }
}
