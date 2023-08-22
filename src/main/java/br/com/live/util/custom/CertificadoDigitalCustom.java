package br.com.live.util.custom;

import javassist.bytecode.ByteArray;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

@Repository
public class CertificadoDigitalCustom {

    private JdbcTemplate jdbcTemplate;

    public CertificadoDigitalCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public byte[] obterCertificadoDigital(int codEmpresa) {
        byte[] certificadoDigitalBytes = null;

        String query = "SELECT a.bytes FROM empr_004 a " +
                "WHERE a.empresa = " + codEmpresa;

        try {
            certificadoDigitalBytes = jdbcTemplate.queryForObject(query, byte[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return certificadoDigitalBytes;
    }
}
