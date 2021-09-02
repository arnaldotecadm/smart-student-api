package br.com.smartstudent.api.service;

import br.com.smartstudent.api.enums.EnumException;
import br.com.smartstudent.api.enums.StatusAprovacaoEnum;
import br.com.smartstudent.api.enums.TipoUsuarioEnum;
import br.com.smartstudent.api.exception.ValidationException;
import br.com.smartstudent.api.model.Usuario;
import br.com.smartstudent.api.repository.AbstractFirebaseRepository;
import br.com.smartstudent.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioService implements RestBasicService<Usuario> {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Usuario> getAll() throws ExecutionException, InterruptedException {
        return this.repository.getAll();
    }

    @Override
    public Optional<Usuario> getById(String id) throws ExecutionException, InterruptedException {
        return this.repository.getById(id);
    }

    @Override
    public Usuario save(Usuario t) {
        return this.repository.save(t);
    }

    @Override
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public AbstractFirebaseRepository getRespository() {
        return repository;
    }

    public boolean aprovarUsuario(String id) throws ExecutionException, InterruptedException {
        return this.updateStatusAprovacaoUsuario(id, StatusAprovacaoEnum.APROVADO);
    }

    public boolean reprovarUsuario(String id) throws ExecutionException, InterruptedException {
        return this.updateStatusAprovacaoUsuario(id, StatusAprovacaoEnum.REPROVADO);
    }

    public Usuario ativarUsuario(String id) throws ExecutionException, InterruptedException {
        return this.updateStatusUsuario(id, true);
    }

    public Usuario desativarUsuario(String id) throws ExecutionException, InterruptedException {
        return this.updateStatusUsuario(id, false);
    }

    public Usuario definirComoAluno(Usuario usuario) throws ExecutionException, InterruptedException {
        return this.updateTipoUsuario(usuario.getDocumentId(), TipoUsuarioEnum.ALUNO);
    }

    public Usuario definirComoProfessor(Usuario usuario) throws ExecutionException, InterruptedException {
        return this.updateTipoUsuario(usuario.getDocumentId(), TipoUsuarioEnum.PROFESSOR);
    }

    private boolean updateStatusAprovacaoUsuario(String id, StatusAprovacaoEnum statusAprovacaoEnum) throws ExecutionException, InterruptedException {
        Usuario usuario = this.getById(id).orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        usuario.setStatusAprovacaoEnum(statusAprovacaoEnum);
        this.save(usuario);
        return true;
    }

    private Usuario updateStatusUsuario(String id, boolean status) throws ExecutionException, InterruptedException {
        Usuario usuario = this.getById(id).orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        usuario.setAtivo(status);
        return this.save(usuario);
    }

    private Usuario updateTipoUsuario(String id, TipoUsuarioEnum tipoUsuarioEnum) throws ExecutionException, InterruptedException {
        Usuario usuario = this.getById(id).orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        usuario.setTipoUsuarioEnum(tipoUsuarioEnum);
        return this.save(usuario);
    }
}
