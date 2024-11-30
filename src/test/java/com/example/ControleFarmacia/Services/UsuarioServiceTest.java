package com.example.ControleFarmacia.Services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;
import com.example.ControleFarmacia.Util.HashUtil;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private CarrinhoRepo carrinhoRepo;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Carrinho carrinho;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setUsername("usuarioTest");
        usuario.setPassword("senhaTest");

        carrinho = new Carrinho();
        carrinho.setId(1);
        carrinho.setUsuario(usuario);
    }

    @Test
    public void testBuscarPorLoginESenha_Sucesso() {
        // Simula o comportamento do repositório
        String senhaHash = HashUtil.gerarHash("senhaTest");
        when(usuarioRepo.findByUsernameAndPassword("usuarioTest", senhaHash))
                .thenReturn(Optional.of(usuario));

        // Chama o método
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorLoginESenha("usuarioTest", "senhaTest");

        // Verifica o resultado
        assertTrue(usuarioOptional.isPresent());
        assertEquals("usuarioTest", usuarioOptional.get().getUsername());
    }

    @Test
    public void testBuscarPorLoginESenha_Falha() {
        // Simula que o repositório não encontrou o usuário
        when(usuarioRepo.findByUsernameAndPassword("usuarioTest", HashUtil.gerarHash("senhaTest")))
                .thenReturn(Optional.empty());

        // Chama o método
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorLoginESenha("usuarioTest", "senhaTest");

        // Verifica o resultado
        assertFalse(usuarioOptional.isPresent());
    }

    @Test
public void testSalvarUsuarioComCarrinho_Sucesso() {
    // Configura os mocks
    when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuario);
    when(carrinhoRepo.save(any(Carrinho.class))).thenReturn(carrinho);

    // Chama o método que você está testando
    usuarioService.salvarUsuarioComCarrinho(usuario, carrinho);

    // Verifica que o repositório de usuários foi chamado duas vezes
    verify(usuarioRepo, times(2)).save(any(Usuario.class));

    // Verifica que o repositório de carrinho foi chamado uma vez
    verify(carrinhoRepo, times(1)).save(any(Carrinho.class));

    // Verifica se o carrinho foi associado ao usuário
    assertEquals(usuario.getCarrinho(), carrinho);

    // Verifica que a senha foi criptografada
    // Compara se a senha do usuário não é igual à senha original
    assertNotEquals(usuario.getPassword(), "senhaOriginal");  // Verifica que a senha foi alterada para o hash

    // (Opcional) Verifica se o hash foi gerado corretamente
    // Isso seria uma verificação mais detalhada, dependendo de como a função `HashUtil.gerarHash` foi implementada
    String senhaHash = HashUtil.gerarHash(usuario.getPassword());
    assertNotNull(senhaHash);  // Verifica que o hash não é nulo
    assertNotEquals(senhaHash, usuario.getPassword());  // Verifica que o hash é diferente da senha original
}
}
