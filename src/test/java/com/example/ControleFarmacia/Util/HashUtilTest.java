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
        String expectedHash = "55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251"; // Hash de "senha123" usando SHA-256

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

    @Test
    public void testGerarHash_ParaEntradaVazia() {
        // Entrada vazia
        String input = "";

        // Gerando o hash para a entrada vazia
        String hashVazio = HashUtil.gerarHash(input);

        // O hash da entrada vazia deve ser um valor conhecido
        String expectedHashVazio = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"; // Hash de "" usando SHA-256

        // Verifica se o hash gerado para a entrada vazia é o esperado
        assertEquals(expectedHashVazio, hashVazio);
    }

    @Test
    public void testGerarHash_ParaEntradaComEspacos() {
        // Entrada com espaços
        String input = " senha123 ";

        // Gerando o hash para a entrada com espaços
        String hashEspacos = HashUtil.gerarHash(input);

        // O hash gerado deve ser específico para a entrada com espaços
        String expectedHashEspacos = "cea21827058147f40cbe5624c1abd20d349cb3483dbf8adf70c7f7d274ed7435"; // Hash de " senha123 " usando SHA-256

        // Verifica se o hash gerado para a entrada com espaços é o esperado
        assertEquals(expectedHashEspacos, hashEspacos);
    }

    @Test
    public void testGerarHash_NaoDeveSerIgualParaEntradasDiferentes() {
        // Entrada 1
        String input1 = "abc123";
        
        // Entrada 2 (letra diferente)
        String input2 = "abc124";

        // Gerando os hashes para as entradas
        String hash1 = HashUtil.gerarHash(input1);
        String hash2 = HashUtil.gerarHash(input2);

        // Verifica se os hashes são diferentes para entradas ligeiramente diferentes
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void testGerarHash_NaoDeveSerIgualParaEntradasComDiferencaDeEspacos() {
        // Entrada 1
        String input1 = "abc 123";
        
        // Entrada 2 com um espaço extra
        String input2 = "abc123 ";

        // Gerando os hashes para as entradas
        String hash1 = HashUtil.gerarHash(input1);
        String hash2 = HashUtil.gerarHash(input2);

        // Verifica se os hashes são diferentes para entradas com espaços diferentes
        assertNotEquals(hash1, hash2);
    }
}
