package com.mete.roadmap.order;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OrderReportWriter {

    public void write(
            Order order,
            Path file
    ) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
        writer.write("Order ID: " + order.getId().value());
        writer.newLine();
        writer.write("Status: " + order.getStatus());
        writer.newLine();
        writer.write("Item count: " + order.getItemCount());
        writer.newLine();
        writer.write("Subtotal: " + order.subtotal());
        writer.newLine();
    }

    }
}
