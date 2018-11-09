package fr.afcepf.al32.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.afcepf.al32.entity.Administrateur;
import fr.afcepf.al32.entity.Association;
import fr.afcepf.al32.entity.Donateur;
import fr.afcepf.al32.entity.Personne;
import fr.afcepf.al32.service.IServiceAdministrateur;

@ManagedBean
@SessionScoped 
public class ConnexionBean 
{
	private String login;
	private String password;
	private String msg;
	
	private boolean redirectionHistorique = false;
	
	private Personne utilisateur;

	@ManagedProperty("#{serviceAdministrateur}")
	private IServiceAdministrateur serviceAdministrateur;
	
	public String login()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		Personne p = serviceAdministrateur.rechercherParConnexion(login, password);
		String suite = null;
		if(p != null)
		{
			msg = "";
			
			if(p instanceof Donateur)
			{
				utilisateur = (Donateur) p;
				context.getExternalContext().getSessionMap().put("user", utilisateur);
				suite="accueilDonateur";
				
				if(redirectionHistorique)
				{
					suite="historiqueDon";
					redirectionHistorique = false;
				}
				
			}
			else if(p instanceof Association)
			{
				utilisateur = (Association) p;
				context.getExternalContext().getSessionMap().put("user", utilisateur);
				suite="accueilAssociation";
			}
			else
			{
				utilisateur = (Administrateur) p;
				context.getExternalContext().getSessionMap().put("user", utilisateur);
				suite="accueilAdmin";
			}

			
		}
		else
		{
			utilisateur = null;
			msg = "Identifiant et/ou Mot de Passe incorrecte";
		}
		System.out.println(suite);
		return suite;
	}
	
	public String accesHistorique()
	{
		if(utilisateur == null)
		{
			redirectionHistorique = true;
			return "login.jsf";
		}
		else
		{
			return "historiqueDon.jsf";
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IServiceAdministrateur getServiceAdministrateur() {
		return serviceAdministrateur;
	}

	public void setServiceAdministrateur(IServiceAdministrateur serviceAdministrateur) {
		this.serviceAdministrateur = serviceAdministrateur;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Personne getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Personne utilisateur) {
		this.utilisateur = utilisateur;
	}

	public boolean isRedirectionHistorique() {
		return redirectionHistorique;
	}

	public void setRedirectionHistorique(boolean redirectionHistorique) {
		this.redirectionHistorique = redirectionHistorique;
	}

}
