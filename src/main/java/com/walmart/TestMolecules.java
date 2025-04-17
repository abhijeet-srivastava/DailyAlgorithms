package com.walmart;

public class TestMolecules {

    public static void main(String[] args) {
        TestMolecules tm = new TestMolecules();
        tm.validateH2O2();
    }

    private void validateH2O2() {
        H2O2 h2O2 = new H2O2();

        // 6 hydrogen threads
        Runnable releaseHydrogen = () -> System.out.print("H");
        for (int i = 0; i < 12; i++) {
            new Thread(() -> {
                try {
                    h2O2.hydrogen(releaseHydrogen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 3 oxygen threads
        Runnable releaseOxygen = () -> System.out.print("O");
        for (int i = 0; i < 12; i++) {
            new Thread(() -> {
                try {
                    h2O2.oxygen(releaseOxygen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
