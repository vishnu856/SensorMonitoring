package com.example.vishnunarayanan.dbapplication;

public class AccelerometerTuple {
    private int _id;
    private long timestamp;
    private float value_x;
    private float value_y;
    private float value_z;

    public AccelerometerTuple(){
    }

    public AccelerometerTuple(long timestamp, float x, float y, float z){
        this.timestamp=timestamp;
        this.value_x=x;
        this.value_y=y;
        this.value_z=z;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public float getValue_x() {
        return value_x;
    }

    public void setValue_x(float value_x) {
        this.value_x = value_x;
    }

    public float getValue_y() {
        return value_y;
    }

    public void setValue_y(float value_y) {
        this.value_y = value_y;
    }

    public float getValue_z() {
        return value_z;
    }

    public void setValue_z(float value_z) {
        this.value_z = value_z;
    }
}
