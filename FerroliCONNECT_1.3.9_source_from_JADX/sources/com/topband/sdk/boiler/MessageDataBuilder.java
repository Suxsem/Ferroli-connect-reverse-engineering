package com.topband.sdk.boiler;

import com.szacs.ferroliconnect.util.ACache;
import com.topband.sdk.boiler.message.boiler.BathWaterMaxTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterMinTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.BoilerControlType;
import com.topband.sdk.boiler.message.boiler.BoilerID;
import com.topband.sdk.boiler.message.boiler.BoilerRelayState;
import com.topband.sdk.boiler.message.boiler.DeviceFunctionType;
import com.topband.sdk.boiler.message.boiler.DeviceInstallationType;
import com.topband.sdk.boiler.message.boiler.DeviceRuntimeInfo;
import com.topband.sdk.boiler.message.boiler.ErrorCode;
import com.topband.sdk.boiler.message.boiler.ErrorInfo;
import com.topband.sdk.boiler.message.boiler.FlameStatus;
import com.topband.sdk.boiler.message.boiler.FlueTemperature;
import com.topband.sdk.boiler.message.boiler.FrozenPreventionStatus;
import com.topband.sdk.boiler.message.boiler.FuelGasType;
import com.topband.sdk.boiler.message.boiler.OutProbeModel;
import com.topband.sdk.boiler.message.boiler.OutTemperature;
import com.topband.sdk.boiler.message.boiler.OutTemperaturePayState;
import com.topband.sdk.boiler.message.boiler.OutWaterTemperature;
import com.topband.sdk.boiler.message.boiler.PWMValue;
import com.topband.sdk.boiler.message.boiler.RecoveryMachineError;
import com.topband.sdk.boiler.message.boiler.ReturnTemperature;
import com.topband.sdk.boiler.message.boiler.RoomThermostatState;
import com.topband.sdk.boiler.message.boiler.SlaveOTSate;
import com.topband.sdk.boiler.message.boiler.WarmingUpStatus;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMaxSettingTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMaxTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMinTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.WaterBottleFrozenPrevention;
import com.topband.sdk.boiler.message.boiler.WaterPressure;
import com.topband.sdk.boiler.message.boiler.WaterRate;
import com.topband.sdk.boiler.message.boiler.WaterSupplySwitch;
import com.topband.sdk.boiler.message.boiler.WindPressureSwitch;
import com.topband.sdk.boiler.message.boiler.WinterSummerMode;
import com.topband.sdk.boiler.message.system.BackLightTime;
import com.topband.sdk.boiler.message.system.BatteryUsage;
import com.topband.sdk.boiler.message.system.DaylightTime;
import com.topband.sdk.boiler.message.system.InDaylightTime;
import com.topband.sdk.boiler.message.system.SystemTime;
import com.topband.sdk.boiler.message.system.SystemTimeStruct;
import com.topband.sdk.boiler.message.system.WiFiConnectState;
import com.topband.sdk.boiler.message.system.WiFiModelSoftVersion;
import com.topband.sdk.boiler.message.system.WiFiSignal;
import com.topband.sdk.boiler.message.thermostat.Boost;
import com.topband.sdk.boiler.message.thermostat.BurnPeriod;
import com.topband.sdk.boiler.message.thermostat.ComfortTemp;
import com.topband.sdk.boiler.message.thermostat.DHWProgram;
import com.topband.sdk.boiler.message.thermostat.EconTemp;
import com.topband.sdk.boiler.message.thermostat.FrostTemp;
import com.topband.sdk.boiler.message.thermostat.FrozenFunction;
import com.topband.sdk.boiler.message.thermostat.FrozenState;
import com.topband.sdk.boiler.message.thermostat.HeatAllow;
import com.topband.sdk.boiler.message.thermostat.HeatMode;
import com.topband.sdk.boiler.message.thermostat.HeatState;
import com.topband.sdk.boiler.message.thermostat.HeatWaterTempMax;
import com.topband.sdk.boiler.message.thermostat.HeatWaterTempMin;
import com.topband.sdk.boiler.message.thermostat.Holiday;
import com.topband.sdk.boiler.message.thermostat.ManualTemp;
import com.topband.sdk.boiler.message.thermostat.NightTemp;
import com.topband.sdk.boiler.message.thermostat.OffFrostTemp;
import com.topband.sdk.boiler.message.thermostat.Override;
import com.topband.sdk.boiler.message.thermostat.OverrideTemp;
import com.topband.sdk.boiler.message.thermostat.PreStart;
import com.topband.sdk.boiler.message.thermostat.Program;
import com.topband.sdk.boiler.message.thermostat.ReturnTempOff;
import com.topband.sdk.boiler.message.thermostat.ReturnTempOn;
import com.topband.sdk.boiler.message.thermostat.RoomCurrentTemp;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTemp;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTempMax;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTempMin;
import com.topband.sdk.boiler.message.thermostat.Season;
import com.topband.sdk.boiler.message.thermostat.TempSensorInfluence;
import com.topband.sdk.boiler.message.thermostat.TempSensorState;
import com.topband.sdk.boiler.message.thermostat.TemperatureBand;
import com.topband.sdk.boiler.message.thermostat.TemperatureChangeRate;
import com.topband.sdk.boiler.message.thermostat.TemperatureOffSetCurve;
import com.topband.sdk.boiler.message.thermostat.TemporaryTempState;
import com.topband.sdk.boiler.message.thermostat.ThermostatID2;
import com.topband.sdk.boiler.message.thermostat.ThermostatRelayStatus;
import com.topband.sdk.boiler.message.thermostat.Unit;
import com.topband.sdk.boiler.message.thermostat.WorkMode;
import java.util.TimeZone;

public class MessageDataBuilder {
    private static final int BUILD_TYPE_CONTROL = 2;
    private static final int BUILD_TYPE_DELETE = 3;
    private static final int BUILD_TYPE_QUERY = 1;
    MessagePacket messagePacket = new MessagePacket(new Message[0]);

    public MessageDataBuilder bathWaterRealTemp(int i) {
        BathWaterRealTemperature bathWaterRealTemperature = new BathWaterRealTemperature();
        bathWaterRealTemperature.setValue((float) i);
        this.messagePacket.addMessage(bathWaterRealTemperature);
        return this;
    }

    public MessageDataBuilder bathWaterTargetTemp(int i) {
        BathWaterTargetTemperature bathWaterTargetTemperature = new BathWaterTargetTemperature();
        bathWaterTargetTemperature.setValue((float) i);
        this.messagePacket.addMessage(bathWaterTargetTemperature);
        return this;
    }

    public MessageDataBuilder bathMaxTemp(int i) {
        BathWaterMaxTemperature bathWaterMaxTemperature = new BathWaterMaxTemperature();
        bathWaterMaxTemperature.setValue(i);
        this.messagePacket.addMessage(bathWaterMaxTemperature);
        return this;
    }

    public MessageDataBuilder bathMinTemp(int i) {
        BathWaterMinTemperature bathWaterMinTemperature = new BathWaterMinTemperature();
        bathWaterMinTemperature.setValue(i);
        this.messagePacket.addMessage(bathWaterMinTemperature);
        return this;
    }

    public MessageDataBuilder deviceError() {
        this.messagePacket.addMessage(new ErrorCode());
        return this;
    }

    public MessageDataBuilder ErrorInfo() {
        this.messagePacket.addMessage(new ErrorInfo());
        return this;
    }

    public MessageDataBuilder deviceFunctionType() {
        this.messagePacket.addMessage(new DeviceFunctionType());
        return this;
    }

    public MessageDataBuilder deviceInstallationType() {
        this.messagePacket.addMessage(new DeviceInstallationType());
        return this;
    }

    public MessageDataBuilder boilerControllType() {
        this.messagePacket.addMessage(new BoilerControlType());
        return this;
    }

    public MessageDataBuilder deviceRuntimeInfo(int i) {
        DeviceRuntimeInfo deviceRuntimeInfo = new DeviceRuntimeInfo();
        deviceRuntimeInfo.setDeviceStatus(i);
        this.messagePacket.addMessage(deviceRuntimeInfo);
        return this;
    }

    public MessageDataBuilder flameStatus(boolean z) {
        FlameStatus flameStatus = new FlameStatus();
        if (z) {
            flameStatus.setFiring();
        } else {
            flameStatus.setDead();
        }
        this.messagePacket.addMessage(flameStatus);
        return this;
    }

    public MessageDataBuilder boiler() {
        this.messagePacket.addMessage(new BoilerID());
        return this;
    }

    public MessageDataBuilder thermostat(String str) {
        ThermostatID2 thermostatID2 = new ThermostatID2();
        thermostatID2.setId(str);
        this.messagePacket.addMessage(thermostatID2);
        return this;
    }

    public MessageDataBuilder TheromstatMode(int i) {
        WorkMode workMode = new WorkMode();
        workMode.setMode(i);
        this.messagePacket.addMessage(workMode);
        return this;
    }

    public MessageDataBuilder TheromstatUnit(int i) {
        Unit unit = new Unit();
        if (i > 0) {
            unit.setFah();
        } else {
            unit.setCen();
        }
        this.messagePacket.addMessage(unit);
        return this;
    }

    public MessageDataBuilder TheromstatManualTemp(float f) {
        ManualTemp manualTemp = new ManualTemp();
        manualTemp.setValue(f);
        this.messagePacket.addMessage(manualTemp);
        return this;
    }

    public MessageDataBuilder TheromstatNightTemp(int i) {
        NightTemp nightTemp = new NightTemp();
        nightTemp.setValue((float) i);
        this.messagePacket.addMessage(nightTemp);
        return this;
    }

    public MessageDataBuilder TheromstatCurrentTemp(int i) {
        RoomCurrentTemp roomCurrentTemp = new RoomCurrentTemp();
        roomCurrentTemp.setValue((float) i);
        this.messagePacket.addMessage(roomCurrentTemp);
        return this;
    }

    public MessageDataBuilder TheromstatTargetTemp(float f) {
        RoomTargetTemp roomTargetTemp = new RoomTargetTemp();
        roomTargetTemp.setValue(f);
        this.messagePacket.addMessage(roomTargetTemp);
        return this;
    }

    public MessageDataBuilder TheromstatTargetMaxTemp(int i) {
        RoomTargetTempMax roomTargetTempMax = new RoomTargetTempMax();
        roomTargetTempMax.setValue((float) i);
        this.messagePacket.addMessage(roomTargetTempMax);
        return this;
    }

    public MessageDataBuilder TheromstatTargetMinTemp(int i) {
        RoomTargetTempMin roomTargetTempMin = new RoomTargetTempMin();
        roomTargetTempMin.setValue((float) i);
        this.messagePacket.addMessage(roomTargetTempMin);
        return this;
    }

    public MessageDataBuilder TheromstatHeatWaterMaxTemp(int i) {
        HeatWaterTempMax heatWaterTempMax = new HeatWaterTempMax();
        heatWaterTempMax.setValue((float) i);
        this.messagePacket.addMessage(heatWaterTempMax);
        return this;
    }

    public MessageDataBuilder TheromstatHeatWaterMinTemp(int i) {
        HeatWaterTempMin heatWaterTempMin = new HeatWaterTempMin();
        heatWaterTempMin.setValue((float) i);
        this.messagePacket.addMessage(heatWaterTempMin);
        return this;
    }

    public MessageDataBuilder TheromstatComfortTemp(float f) {
        ComfortTemp comfortTemp = new ComfortTemp();
        comfortTemp.setValue(f);
        this.messagePacket.addMessage(comfortTemp);
        return this;
    }

    public MessageDataBuilder TheromstatEconTemp(float f) {
        EconTemp econTemp = new EconTemp();
        econTemp.setValue(f);
        this.messagePacket.addMessage(econTemp);
        return this;
    }

    public MessageDataBuilder TheromstatFrostTemp(float f) {
        FrostTemp frostTemp = new FrostTemp();
        frostTemp.setValue(f);
        this.messagePacket.addMessage(frostTemp);
        return this;
    }

    public MessageDataBuilder TheromstatOffFrostTemp(float f) {
        OffFrostTemp offFrostTemp = new OffFrostTemp();
        offFrostTemp.setValue(f);
        this.messagePacket.addMessage(offFrostTemp);
        return this;
    }

    public MessageDataBuilder TheromstatBoost(boolean z) {
        Boost boost = new Boost();
        boost.setActive(z);
        this.messagePacket.addMessage(boost);
        return this;
    }

    public MessageDataBuilder TheromstatOverride(boolean z) {
        Override override = new Override();
        override.setActive(z);
        this.messagePacket.addMessage(override);
        return this;
    }

    public MessageDataBuilder TheromstatOverrideTemp(float f) {
        OverrideTemp overrideTemp = new OverrideTemp();
        overrideTemp.setValue(f);
        this.messagePacket.addMessage(overrideTemp);
        return this;
    }

    public MessageDataBuilder TheromstatSeason(boolean z) {
        Season season = new Season();
        season.setWinterOrSummer(z);
        this.messagePacket.addMessage(season);
        return this;
    }

    public MessageDataBuilder TheromstatHeatState(int i) {
        HeatState heatState = new HeatState();
        heatState.setState(i);
        this.messagePacket.addMessage(heatState);
        return this;
    }

    public MessageDataBuilder TheromstatHeatMode(int i) {
        HeatMode heatMode = new HeatMode();
        heatMode.setMode(i);
        this.messagePacket.addMessage(heatMode);
        return this;
    }

    public MessageDataBuilder TheromstatHeatAllow(boolean z) {
        HeatAllow heatAllow = new HeatAllow();
        heatAllow.setEnable(z);
        this.messagePacket.addMessage(heatAllow);
        return this;
    }

    public MessageDataBuilder TheromstatHoliday(long j, long j2) {
        Holiday holiday = new Holiday();
        holiday.setEnable((byte) 1);
        holiday.setEndTime((int) j);
        holiday.setStartTime((int) j2);
        this.messagePacket.addMessage(holiday);
        return this;
    }

    public MessageDataBuilder TheromstatTime(long j) {
        SystemTime systemTime = new SystemTime();
        systemTime.setSeconds((int) j);
        this.messagePacket.addMessage(systemTime);
        return this;
    }

    public MessageDataBuilder TheromstatRelay(boolean z) {
        ThermostatRelayStatus thermostatRelayStatus = new ThermostatRelayStatus();
        if (z) {
            thermostatRelayStatus.setOpen();
        } else {
            thermostatRelayStatus.setClosed();
        }
        this.messagePacket.addMessage(thermostatRelayStatus);
        return this;
    }

    public MessageDataBuilder TheromstatFrozenAllow(boolean z) {
        FrozenFunction frozenFunction = new FrozenFunction();
        frozenFunction.setEnable(z);
        this.messagePacket.addMessage(frozenFunction);
        return this;
    }

    public MessageDataBuilder TheromstatTempBand(int i) {
        TemperatureBand temperatureBand = new TemperatureBand();
        temperatureBand.setBand(i);
        this.messagePacket.addMessage(temperatureBand);
        return this;
    }

    public MessageDataBuilder TheromstatTempChangeRate(int i) {
        TemperatureChangeRate temperatureChangeRate = new TemperatureChangeRate();
        temperatureChangeRate.setPeriod(i);
        this.messagePacket.addMessage(temperatureChangeRate);
        return this;
    }

    public MessageDataBuilder TheromstatTempCurve(int i) {
        TemperatureOffSetCurve temperatureOffSetCurve = new TemperatureOffSetCurve();
        temperatureOffSetCurve.setNumber(i);
        this.messagePacket.addMessage(temperatureOffSetCurve);
        return this;
    }

    public MessageDataBuilder TheromstatTemporaryState(boolean z) {
        TemporaryTempState temporaryTempState = new TemporaryTempState();
        temporaryTempState.setActive(z);
        this.messagePacket.addMessage(temporaryTempState);
        return this;
    }

    public MessageDataBuilder TheromstatSensorInfluence(int i) {
        TempSensorInfluence tempSensorInfluence = new TempSensorInfluence();
        tempSensorInfluence.setValue(i);
        this.messagePacket.addMessage(tempSensorInfluence);
        return this;
    }

    public MessageDataBuilder TheromstatSensorState(boolean z) {
        TempSensorState tempSensorState = new TempSensorState();
        tempSensorState.setActive(z);
        this.messagePacket.addMessage(tempSensorState);
        return this;
    }

    public MessageDataBuilder TheromstatReturnTempOn(int i) {
        ReturnTempOn returnTempOn = new ReturnTempOn();
        returnTempOn.setValue(i);
        this.messagePacket.addMessage(returnTempOn);
        return this;
    }

    public MessageDataBuilder TheromstatReturnTempOff(int i) {
        ReturnTempOff returnTempOff = new ReturnTempOff();
        returnTempOff.setValue(i);
        this.messagePacket.addMessage(returnTempOff);
        return this;
    }

    public MessageDataBuilder ReturnWaterTemp(int i) {
        ReturnTemperature returnTemperature = new ReturnTemperature();
        returnTemperature.setValue((float) i);
        this.messagePacket.addMessage(returnTemperature);
        return this;
    }

    public MessageDataBuilder OutWaterTemp(int i) {
        OutWaterTemperature outWaterTemperature = new OutWaterTemperature();
        outWaterTemperature.setValue((float) i);
        this.messagePacket.addMessage(outWaterTemperature);
        return this;
    }

    public MessageDataBuilder TheromstatBurnPeroid(int i) {
        BurnPeriod burnPeriod = new BurnPeriod();
        burnPeriod.setRate(i);
        this.messagePacket.addMessage(burnPeriod);
        return this;
    }

    public MessageDataBuilder TheromstatDHWProgram(int i) {
        this.messagePacket.addMessage(new DHWProgram());
        return this;
    }

    public MessageDataBuilder TheromstatPrestart(int i) {
        PreStart preStart = new PreStart();
        preStart.setValue(i);
        this.messagePacket.addMessage(preStart);
        return this;
    }

    public MessageDataBuilder TheromstatProgram(int i) {
        this.messagePacket.addMessage(Program.newInstance((short) i));
        return this;
    }

    public MessageDataBuilder TheromstatProgram(int i, int[] iArr) {
        Program newInstance = Program.newInstance((short) i);
        newInstance.setData(iArr);
        this.messagePacket.addMessage(newInstance);
        return this;
    }

    public MessageDataBuilder TheromstatFrozenState(boolean z) {
        FrozenState frozenState = new FrozenState();
        frozenState.setActive(z);
        this.messagePacket.addMessage(frozenState);
        return this;
    }

    public MessageDataBuilder frozenPreventionStatus(boolean z) {
        FrozenPreventionStatus frozenPreventionStatus = new FrozenPreventionStatus();
        if (z) {
            frozenPreventionStatus.setOpen();
        } else {
            frozenPreventionStatus.setClosed();
        }
        this.messagePacket.addMessage(frozenPreventionStatus);
        return this;
    }

    public MessageDataBuilder fuelGasType() {
        this.messagePacket.addMessage(new FuelGasType());
        return this;
    }

    public MessageDataBuilder FlueTemperature(int i) {
        FlueTemperature flueTemperature = new FlueTemperature();
        flueTemperature.setValue(i);
        this.messagePacket.addMessage(flueTemperature);
        return this;
    }

    public MessageDataBuilder WaterRate(float f) {
        WaterRate waterRate = new WaterRate();
        waterRate.setValue(f);
        this.messagePacket.addMessage(waterRate);
        return this;
    }

    public MessageDataBuilder pwmValue(int i) {
        PWMValue pWMValue = new PWMValue();
        pWMValue.set(i);
        this.messagePacket.addMessage(pWMValue);
        return this;
    }

    public MessageDataBuilder recoveryMachineError() {
        this.messagePacket.addMessage(new RecoveryMachineError());
        return this;
    }

    public MessageDataBuilder warmingUpStatus(boolean z) {
        WarmingUpStatus warmingUpStatus = new WarmingUpStatus();
        if (z) {
            warmingUpStatus.setOpen();
        } else {
            warmingUpStatus.setClosed();
        }
        this.messagePacket.addMessage(warmingUpStatus);
        return this;
    }

    public MessageDataBuilder warmingWaterRealTemp(int i) {
        WarmingWaterRealTemperature warmingWaterRealTemperature = new WarmingWaterRealTemperature();
        warmingWaterRealTemperature.setValue((float) i);
        this.messagePacket.addMessage(warmingWaterRealTemperature);
        return this;
    }

    public MessageDataBuilder warmingWaterTargetTemp(int i) {
        WarmingWaterTargetTemperature warmingWaterTargetTemperature = new WarmingWaterTargetTemperature();
        warmingWaterTargetTemperature.setValue((float) i);
        this.messagePacket.addMessage(warmingWaterTargetTemperature);
        return this;
    }

    public MessageDataBuilder warmingMaxTemp(int i) {
        WarmingWaterMaxTemperature warmingWaterMaxTemperature = new WarmingWaterMaxTemperature();
        warmingWaterMaxTemperature.setValue(i);
        this.messagePacket.addMessage(warmingWaterMaxTemperature);
        return this;
    }

    public MessageDataBuilder warmingMinTemp(int i) {
        WarmingWaterMinTemperature warmingWaterMinTemperature = new WarmingWaterMinTemperature();
        warmingWaterMinTemperature.setValue(i);
        this.messagePacket.addMessage(warmingWaterMinTemperature);
        return this;
    }

    public MessageDataBuilder warmingMaxSetPoint(int i) {
        WarmingWaterMaxSettingTemperature warmingWaterMaxSettingTemperature = new WarmingWaterMaxSettingTemperature();
        warmingWaterMaxSettingTemperature.setValue((float) i);
        this.messagePacket.addMessage(warmingWaterMaxSettingTemperature);
        return this;
    }

    public MessageDataBuilder waterBottleFrozenPrevention(boolean z) {
        WaterBottleFrozenPrevention waterBottleFrozenPrevention = new WaterBottleFrozenPrevention();
        if (z) {
            waterBottleFrozenPrevention.setOpen();
        } else {
            waterBottleFrozenPrevention.setClosed();
        }
        this.messagePacket.addMessage(waterBottleFrozenPrevention);
        return this;
    }

    public MessageDataBuilder waterPressure(float f) {
        WaterPressure waterPressure = new WaterPressure();
        waterPressure.setValue(f);
        this.messagePacket.addMessage(waterPressure);
        return this;
    }

    public MessageDataBuilder outTemp(float f) {
        OutTemperature outTemperature = new OutTemperature();
        outTemperature.setValue(f);
        this.messagePacket.addMessage(outTemperature);
        return this;
    }

    public MessageDataBuilder waterSupplySwitch(boolean z) {
        WaterSupplySwitch waterSupplySwitch = new WaterSupplySwitch();
        if (z) {
            waterSupplySwitch.setOpen();
        } else {
            waterSupplySwitch.setClosed();
        }
        this.messagePacket.addMessage(waterSupplySwitch);
        return this;
    }

    public MessageDataBuilder windPressureSwitch(boolean z) {
        WindPressureSwitch windPressureSwitch = new WindPressureSwitch();
        if (z) {
            windPressureSwitch.setOpen();
        } else {
            windPressureSwitch.setClosed();
        }
        this.messagePacket.addMessage(windPressureSwitch);
        return this;
    }

    public MessageDataBuilder winterSummerMode(int i) {
        WinterSummerMode winterSummerMode = new WinterSummerMode();
        winterSummerMode.setMode(i);
        this.messagePacket.addMessage(winterSummerMode);
        return this;
    }

    public MessageDataBuilder systemTimeSeconds(int i) {
        SystemTime systemTime = new SystemTime();
        systemTime.setSeconds(i);
        this.messagePacket.addMessage(systemTime);
        return this;
    }

    public MessageDataBuilder systemTimeStruct(int i) {
        SystemTimeStruct systemTimeStruct = new SystemTimeStruct();
        systemTimeStruct.setSecond((byte) i);
        this.messagePacket.addMessage(systemTimeStruct);
        return this;
    }

    public MessageDataBuilder wifiConnectState(int i) {
        WiFiConnectState wiFiConnectState = new WiFiConnectState();
        wiFiConnectState.setData((byte) i);
        this.messagePacket.addMessage(wiFiConnectState);
        return this;
    }

    public MessageDataBuilder wifiSignal(int i) {
        WiFiSignal wiFiSignal = new WiFiSignal();
        wiFiSignal.setData((byte) i);
        this.messagePacket.addMessage(wiFiSignal);
        return this;
    }

    public MessageDataBuilder slaveOTState(int i) {
        SlaveOTSate slaveOTSate = new SlaveOTSate();
        slaveOTSate.setData((byte) i);
        this.messagePacket.addMessage(slaveOTSate);
        return this;
    }

    public MessageDataBuilder roomThermostatState(boolean z) {
        RoomThermostatState roomThermostatState = new RoomThermostatState();
        roomThermostatState.setActive(z);
        this.messagePacket.addMessage(roomThermostatState);
        return this;
    }

    public MessageDataBuilder daylightTime(boolean z) {
        DaylightTime daylightTime = new DaylightTime();
        if (z) {
            daylightTime.setOpen();
        } else {
            daylightTime.setClosed();
        }
        this.messagePacket.addMessage(daylightTime);
        return this;
    }

    public MessageDataBuilder inDaylightTime(int i) {
        InDaylightTime inDaylightTime = new InDaylightTime();
        inDaylightTime.setData((byte) i);
        this.messagePacket.addMessage(inDaylightTime);
        return this;
    }

    public MessageDataBuilder batteryUsage(int i) {
        BatteryUsage batteryUsage = new BatteryUsage();
        batteryUsage.setData((byte) i);
        this.messagePacket.addMessage(batteryUsage);
        return this;
    }

    public MessageDataBuilder backLightTime(int i) {
        BackLightTime backLightTime = new BackLightTime();
        backLightTime.setData((byte) i);
        this.messagePacket.addMessage(backLightTime);
        return this;
    }

    public MessageDataBuilder outTemperaturePayState(int i) {
        OutTemperaturePayState outTemperaturePayState = new OutTemperaturePayState();
        outTemperaturePayState.setData((byte) i);
        this.messagePacket.addMessage(outTemperaturePayState);
        return this;
    }

    public MessageDataBuilder outTempProbeModel(int i) {
        OutProbeModel outProbeModel = new OutProbeModel();
        outProbeModel.setData((byte) i);
        this.messagePacket.addMessage(outProbeModel);
        return this;
    }

    public MessageDataBuilder WiFiModelSoftVersion() {
        this.messagePacket.addMessage(new WiFiModelSoftVersion());
        return this;
    }

    public MessageDataBuilder boilerRelayState(int i) {
        BoilerRelayState boilerRelayState = new BoilerRelayState();
        if (i == 2) {
            boilerRelayState.setOpen();
        } else {
            boilerRelayState.setClosed();
        }
        this.messagePacket.addMessage(boilerRelayState);
        return this;
    }

    public MessageDataBuilder setVersion(int i) {
        this.messagePacket.setVersionCode(i);
        return this;
    }

    public MessageDataBuilder warmingWaterMaxTemperature() {
        this.messagePacket.addMessage(new WarmingWaterMaxTemperature());
        return this;
    }

    public MessageDataBuilder warmingWaterMinTemperature() {
        this.messagePacket.addMessage(new WarmingWaterMinTemperature());
        return this;
    }

    public MessageDataBuilder bathWaterMaxTemperature() {
        this.messagePacket.addMessage(new BathWaterMaxTemperature());
        return this;
    }

    public MessageDataBuilder bathWaterMinTemperature() {
        this.messagePacket.addMessage(new BathWaterMinTemperature());
        return this;
    }

    public byte[] query() {
        return build(1);
    }

    public byte[] delete() {
        return build(3);
    }

    public byte[] control() {
        return build(2);
    }

    private byte[] build(int i) {
        if (i == 1) {
            this.messagePacket.setTypeQuery();
        } else if (i == 2) {
            this.messagePacket.setTypeControl();
        } else if (i != 3) {
            this.messagePacket = null;
        } else {
            this.messagePacket.setTypeDelete();
        }
        MessagePacket.COUNT = (short) (MessagePacket.COUNT + 1);
        if (MessagePacket.COUNT > 255) {
            MessagePacket.COUNT = 0;
        }
        this.messagePacket.setMessageId(MessagePacket.COUNT);
        this.messagePacket.setTimeZone(((TimeZone.getDefault().getRawOffset() / 1000) / ACache.TIME_HOUR) + 12);
        this.messagePacket.setTime(System.currentTimeMillis());
        this.messagePacket.setWifiStamp(0);
        MessagePacket messagePacket2 = this.messagePacket;
        messagePacket2.setInfoLength(messagePacket2.getInfoLength());
        MessagePacket messagePacket3 = this.messagePacket;
        if (messagePacket3 != null) {
            return messagePacket3.toBytes();
        }
        return null;
    }
}
