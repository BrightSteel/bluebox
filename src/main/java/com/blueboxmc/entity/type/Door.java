package com.blueboxmc.entity.type;

import com.blueboxmc.state.DoorState;

public interface Door {

    float getDoorOpenValue();
    void setDoorOpenValue(float doorOpenValue);

    DoorState getDoorState();
    void setDoorState(DoorState doorState);

    void sendObservedUpdatePacket();
}
