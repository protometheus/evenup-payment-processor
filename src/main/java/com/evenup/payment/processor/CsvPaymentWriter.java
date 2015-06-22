package com.evenup.payment.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;

import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.evenup.payment.processor.dto.PaymentDTO;
import com.google.common.collect.ImmutableMap;

public class CsvPaymentWriter implements PaymentWriter {
    
    private final CsvDozerBeanWriter beanWriter;
    
    /**
     * 
     * @param writer - will be wrapped in a {@link BufferedWriter}.
     * @throws IOException
     */
    public CsvPaymentWriter(
            final Writer writer, 
            final ImmutableMap<String, String> headerToBeanField) throws IOException {
        final String[] fieldMappings = new String[headerToBeanField.size()];
        final String[] headers = new String[headerToBeanField.size()];
        int i = 0;
        for (Entry<String, String> entry : headerToBeanField.entrySet()) {
            fieldMappings[i] = entry.getValue();
            headers[i++] = entry.getKey();
        }
        beanWriter = new CsvDozerBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
        beanWriter.configureBeanMapping(PaymentDTO.class, fieldMappings);
        writeHeader(headers);
    }
    
    private synchronized void writeHeader(String[] headers) throws IOException {
        beanWriter.writeHeader(headers);
        beanWriter.flush();
    }
    
    public synchronized void write(PaymentDTO dto) throws IOException {
        beanWriter.write(dto);
        beanWriter.flush(); // TODO consider removing...
    }
    
    public synchronized void close() throws IOException {
        if (beanWriter != null)
            beanWriter.close(); 
    }

}
