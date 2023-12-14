package com.szacs.ferroliconnect.viewInterface;

public interface ThermostatProgramView {
    void applyProgramToOtherDays(boolean[] zArr);

    int getGatewayPosition();

    float[] getProgramEditData();

    int getThermostatPosition();

    void onApplyProgramFailed(int i, boolean z);

    void onApplyProgramSuccess();

    void onGetThermostatProgramFailed(int i, boolean z);

    void onGetThermostatProgramSuccess(int i);

    void onSetThermostatProgramFailed(int i, boolean z);

    void onSetThermostatProgramSuccess(int i);

    void setProgramEditData(int i, int i2, int i3, int i4);
}
