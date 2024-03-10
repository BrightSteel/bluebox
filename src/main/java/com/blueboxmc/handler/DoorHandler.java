package com.blueboxmc.handler;

import com.blueboxmc.entity.type.Door;
import com.blueboxmc.entity.type.PortalDoor;
import com.blueboxmc.state.DoorState;

public class DoorHandler {

    private final Door door;

    public DoorHandler(Door door) {
        this.door = door;
    }

    public void tick(boolean isClient) {
        DoorState doorState = door.getDoorState();
        float doorOpenValue = door.getDoorOpenValue();

        if (doorState == DoorState.OPEN || doorState == DoorState.CLOSED) {
            return;
        }
        int direction = doorState == DoorState.OPENING ? 1 : -1;

        if (doorState == DoorState.OPENING && doorOpenValue >= 1.5) {
            door.setDoorState(DoorState.OPEN);
        } else if (doorState == DoorState.CLOSING && doorOpenValue <= 0.1) {
            door.setDoorState(DoorState.CLOSED);
            door.setDoorOpenValue(0);
            if (door instanceof PortalDoor portalDoor && !isClient) {
                portalDoor.closePortal();
            }
        } else {
            door.setDoorOpenValue(door.getDoorOpenValue() + direction * 0.2f);
        }
    }

    public void toggleDoor() {
        switch (door.getDoorState()) {
            case OPEN, OPENING -> closeDoor();
            case CLOSED, CLOSING -> openDoor();
        }
    }

    public void openDoor() {
        if (door instanceof PortalDoor portalDoor) {
            portalDoor.openPortal();
        }
        door.setDoorState(DoorState.OPENING);
        door.sendObservedUpdatePacket();
    }

    public void closeDoor() {
        door.setDoorState(DoorState.CLOSING);
        door.sendObservedUpdatePacket();
    }
}
