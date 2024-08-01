package com.advent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BillionRowChallenge {
    public static void main(String[] args) {
        BillionRowChallenge brc = new BillionRowChallenge();
        brc.execute();
    }

    public class MeasurementAggregator {
        private double min = Double.POSITIVE_INFINITY;
        private double max = Double.NEGATIVE_INFINITY;

        private double sum = 0.0d;
        private  long count = 0l;

    }
    private record ResultRow(double min, double mean, double max) {
        public String toString() {
            return round(min) + "/" + round(mean) + "/" + round(max);
        }
        public double round(double val) {
            return Math.round(val*10.0d)/10.0d;
        }
    };

    private class Measurement {
        String station;
        Double temp;

        public Measurement(String[] split) {
            this.station = split[0].trim();
            this.temp = Double.valueOf(split[1]);
        }

        public Measurement(String station, double temp) {
            this.station = station;
            this.temp = temp;
        }
    }
    private void execute() {
        try {
            Collector<Measurement, MeasurementAggregator, ResultRow> collector = Collector.of(
                MeasurementAggregator::new,
                    (a, m) -> {
                        a.min = Math.min(a.min, m.temp);
                        a.max = Math.min(a.max, m.temp);
                        a.sum += m.temp;
                        a.count += 1;
                    },
                    (agg1, agg2) -> {
                        var res = new MeasurementAggregator();
                        res.min = Math.min(agg1.min, agg2.min);
                        res.max = Math.max(agg1.max, agg2.max);
                        res.sum = agg1.sum + agg2.sum;
                        res.count = agg1.count + agg2.count;
                        return res;
                    },
                    agg -> {
                        return new ResultRow(agg.min, agg.sum/agg.count, agg.max);
                    }
            );
            Map<String, ResultRow> measurements = new TreeMap<>(
                    Files.lines(Paths.get("src/main/resources/weather_stations.csv")).parallel()
                            .map(record -> {
                                int pivotIdx = record.indexOf(";");
                                String station = record.substring(0, pivotIdx).trim();
                                double temp = Double.parseDouble(record.substring(pivotIdx+1));
                                return new Measurement(station, temp);
                            })
                            .collect(Collectors.groupingBy(m -> m.station, collector))
            );
            //System.out.println(measurements);
            BufferedWriter out = new BufferedWriter(new FileWriter("src/main/resources/station_report.csv"), 32768);
            for(var t: measurements.entrySet()) {
                //System.out.printf("%s - %s\n", t.getKey(), t.getValue().toString());
                out.write(t.getKey() + ":" + t.getValue().toString() + '\n');
            }
            out.close();
            /*for(var t: measurements.entrySet()) {
                System.out.printf("%s - %s\n", t.getKey(), t.getValue().toString());
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
