package com.example.ControleFarmacia.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class HashUtilTest {

    @Test
    public void testGerarHash() {
        // Entrada conhecida
        String input = "senha123";

        // O hash gerado deve ser sempre o mesmo para a mesma entrada
        String expectedHash = "ef92b778bafe771e89245b7b7d6b557a9d264d5f05e9a60b1fced6583cbb40fd"; // Hash de "senha123" usando SHA-256

        // Gerando o hash usando o HashUtil
        String generatedHash = HashUtil.gerarHash(input);

        // Verifica se o hash gerado é o esperado
        assertEquals(expectedHash, generatedHash);
    }

    @Test
    public void testGerarHash_DeveRetornarDiferenteParaEntradaDiferente() {
        // Entradas diferentes
        String input1 = "senha123";
        String input2 = "senha124";

        // Gerando hashes para as entradas
        String hash1 = HashUtil.gerarHash(input1);
        String hash2 = HashUtil.gerarHash(input2);

        // Verifica se os hashes gerados para entradas diferentes são diferentes
        assertNotEquals(hash1, hash2);
    }
}
