package br.com.smartstudent.api.service;

import br.com.smartstudent.api.enums.EnumException;
import br.com.smartstudent.api.enums.StatusAprovacaoEnum;
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
        return this.updateStatusUsuario(id, StatusAprovacaoEnum.APROVADO);
    }

    public boolean reprovarUsuario(String id) throws ExecutionException, InterruptedException {
        return this.updateStatusUsuario(id, StatusAprovacaoEnum.REPROVADO);
    }

    private boolean updateStatusUsuario(String id, StatusAprovacaoEnum statusAprovacaoEnum) throws ExecutionException, InterruptedException {
        Usuario usuario = this.getById(id).orElseThrow(() -> new ValidationException(EnumException.ITEM_NAO_ENCONTRADO));
        usuario.setStatusAprovacaoEnum(statusAprovacaoEnum);
        this.save(usuario);
        return true;
    }
}
