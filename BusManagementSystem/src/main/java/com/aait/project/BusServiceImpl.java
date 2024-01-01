package com.aait.project;

import java.util.*;


public class BusServiceImpl implements BusService {
    private Map<String, Bus> buses;

    public BusServiceImpl() {
        this.buses = new HashMap<>();
    }

    @Override
    public List<Bus> getAllBuses() {
        return new ArrayList<>(buses.values());
    }

    @Override
    public Bus getBusById(String busId) {
        return buses.get(busId);
    }

    @Override
    public void addBus(Bus bus) {
        buses.put(bus.getBusId(), bus);
    }

    @Override
    public void updateBus(Bus bus) {
        buses.put(bus.getBusId(), bus);
    }

    @Override
    public void deleteBus(String busId) {
        buses.remove(busId);
    }
}