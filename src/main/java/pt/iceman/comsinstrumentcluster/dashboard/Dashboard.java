package pt.iceman.comsinstrumentcluster.dashboard;

import eu.hansolo.medusa.Gauge;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.comsinstrumentcluster.screen.AbsolutePositioning;
import pt.iceman.comsinstrumentcluster.screen.Screen;
import pt.iceman.middleware.cars.BaseCommand;

import java.util.concurrent.ExecutionException;

public abstract class Dashboard extends Screen {
    private static final Logger logger = LogManager.getLogger(Dashboard.class);
    protected State state;
    protected Gauge speedGauge;
    protected AbsolutePositioning speedGaugeAbsPos;
    protected double speed;
    protected Gauge tempGauge;
    protected AbsolutePositioning tempGaugeAbsPos;
    protected double temp;
    protected Image tempImage;
    protected ImageView tempImageView;
    protected AbsolutePositioning tempImageAbsPos;
    protected Gauge rpmGauge;
    protected AbsolutePositioning rpmGaugeAbsPos;
    protected double rpm;
    protected Gauge fuelGauge;
    protected AbsolutePositioning dieselGaugeAbsPos;
    protected double fuel;
    protected Image fuelImage;
    protected ImageView fuelImageViewer;
    protected AbsolutePositioning fuelImageAbsPos;
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

        Platform.runLater(() -> {
            this.speedGauge.setValue(speed);
            logger.debug("Set speed value {}", speed);
        });
    }

    public Double getRpm() {
        return this.rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;

        Platform.runLater(() -> {
            this.rpmGauge.setValue(rpm);
            logger.debug("Set rpm value {}", rpm);
        });
    }

    public synchronized double getTotalDistance() {
        return this.totalDistance;
    }

    public synchronized void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;

        Platform.runLater(() -> {
            this.totalDistanceLcd.setValue(totalDistance);
            logger.debug("Set total distance {}", totalDistance);
        });
    }

    public double getTemp() {
        return this.temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;

        Platform.runLater(() -> {
            this.tempGauge.setValue(temp);
            logger.debug("Set temperature {}", temp);
        });
    }

    public double getFuel() {
        return this.fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
        Platform.runLater(() -> {
            this.fuelGauge.setValue(fuel);
            logger.debug("Set fuel {}", fuelGauge);
        });
    }

    public boolean isBrakesOil() {
        return this.brakesOil;
    }

    public void setBrakesOil(boolean brakesOil) {
        this.brakesOil = brakesOil;
        Platform.runLater(() -> {
            this.brakesOilImageView.setVisible(brakesOil);
            logger.debug("Set brakes oil {}", brakesOil);
        });
    }

    public boolean isBattery() {
        return this.battery;
    }

    public void setBattery(boolean battery) {
        this.battery = battery;
        Platform.runLater(() -> {
            this.batteryImageView.setVisible(battery);
            logger.debug("Set battery {}", battery);
        });
    }

    public boolean isAbs() {
        return this.abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
        Platform.runLater(() -> {
            this.absImageView.setVisible(abs);
            logger.debug("Set ABS {}", abs);
        });
    }

    public boolean isParking() {
        return this.parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
        Platform.runLater(() -> {
            this.parkingImageView.setVisible(parking);
            logger.debug("Set parking {}", parking);
        });
    }

    public boolean isHighBeams() {
        return this.highBeams;
    }

    public void setHighBeams(boolean highBeams) {
        this.highBeams = highBeams;
        Platform.runLater(() -> {
            this.highBeamsImageView.setVisible(highBeams);
            logger.debug("Set highbeams {}", highBeams);
        });
    }

    public boolean isOilPressure() {
        return this.oilPressure;
    }

    public void setOilPressure(boolean oilPressure) {
        this.oilPressure = oilPressure;
        Platform.runLater(() -> {
            this.oilPressureImageView.setVisible(oilPressure);
            logger.debug("Set oil pressure {}", oilPressure);
        });
    }

    public boolean isSparkPlug() {
        return this.sparkPlug;
    }

    public void setSparkPlug(boolean sparkPlug) {
        this.sparkPlug = sparkPlug;
        Platform.runLater(() -> {
            this.sparkPlugImageView.setVisible(sparkPlug);
            logger.debug("Set spark plug {}", sparkPlug);
        });
    }

    public boolean isTurnSigns() {
        return this.turnSigns;
    }

    public void setTurnSigns(boolean turnSigns) {
        this.turnSigns = turnSigns;
        Platform.runLater(() -> {
            this.turningSignsImageView.setVisible(turnSigns);
            logger.debug("Set turn signs {}", turnSigns);
        });
    }

    public void setIce(boolean ice) {
        this.ice = ice;
        Platform.runLater(() -> {
            this.iceImageView.setVisible(ice);
            logger.debug("Set ice {}", ice);
        });
    }

    public void configureInstruments() {}

    public <T extends BaseCommand> void applyCommand(T baseCommand) throws ExecutionException, InterruptedException {
        setBattery(baseCommand.isBattery12vNotCharging());
        setParking(baseCommand.isParkingBrakeOn());
        setBrakesOil(baseCommand.isBrakesHydraulicFluidLevelLow());
        setTurnSigns(baseCommand.isTurningSigns());
        setAbs(baseCommand.isAbsAnomaly());
        setHighBeams(baseCommand.isHighBeamOn());
        setSpeed(baseCommand.getSpeed());
        setTotalDistance(baseCommand.getTotalDistance());
    }
}
