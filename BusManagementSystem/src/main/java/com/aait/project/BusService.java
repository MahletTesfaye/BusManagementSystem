package com.aait.project;

import java.util.List;


public interface BusService {
    List<Bus> getAllBuses();

    Bus getBusById(String busId);

    void addBus(Bus bus);

    void updateBus(Bus bus);

    void deleteBus(String busId);
}