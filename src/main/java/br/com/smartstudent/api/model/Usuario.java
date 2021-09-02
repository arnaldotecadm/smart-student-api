package br.com.smartstudent.api.model;

import br.com.smartstudent.api.enums.StatusAprovacaoEnum;
import br.com.smartstudent.api.enums.TipoUsuarioEnum;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Usuario extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 4408418647685225829L;
    private String uid;
    private String nome;
    private String email;
    private boolean isEmailVerified;
    private String issuer;
    private String picture;
    private boolean ativo;
    private TipoUsuarioEnum tipoUsuarioEnum;
    private StatusAprovacaoEnum statusAprovacaoEnum;

    public Usuario(FirebaseToken firebaseToken) throws FirebaseAuthException {
        this.uid = firebaseToken.getUid();
        this.nome = null != firebaseToken.getName() ? firebaseToken.getName() : FirebaseAuth.getInstance().getUser(firebaseToken.getUid()).getDisplayName();
        this.email = firebaseToken.getEmail();
        this.isEmailVerified = firebaseToken.isEmailVerified();
        this.issuer = firebaseToken.getIssuer();
        this.picture = firebaseToken.getPicture();
        this.ativo = true;
        this.tipoUsuarioEnum = TipoUsuarioEnum.VISITANTE;
        this.statusAprovacaoEnum = StatusAprovacaoEnum.PENDENTE;
    }
}