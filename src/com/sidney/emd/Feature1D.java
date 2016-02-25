package com.sidney.emd;


public class Feature1D implements Feature {
    private double x;

    public Feature1D(double x) {
        this.x = x;
    }
    
    public double groundDist(Feature f) {
        Feature1D f1d = (Feature1D)f;
        return 1.0;
    }
}