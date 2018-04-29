package com.example.dell.elixir;

/**
 * Created by Ezio on 4/6/2018.
 */

public class sensorData {



        private float temperature;
        private float pH;
        private float turbidity;

        public sensorData() {
        }

        public sensorData(float temperature, float pH, float turbidity){
            this.temperature = temperature;
            this.pH = pH;
            this.turbidity = turbidity;
        }
    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getpH() {
        return pH;
    }

    public void setpH(float pH) {
        this.pH = pH;
    }

    public float getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(float turbidity) {
        this.turbidity = turbidity;
    }
}

