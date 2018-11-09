package fr.afcepf.al32.web;

import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.afcepf.al32.entity.PackAssociation;
import fr.afcepf.al32.entity.Personne;
import fr.afcepf.al32.service.IServicePack;


@ManagedBean 
@RequestScoped 
public class ListePacksAssociationBean {
	
	private Personne utilisateur = (Personne) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
	
	@ManagedProperty(value ="#{servicePackImpl}") //#{nomComposantJsfOuSpring} //nomClasseJava avec minuscule au debut
	private IServicePack servicePack;
	private List<PackAssociation> packs;
	
	@PostConstruct
	public void init()
	{		
		//System.out.println("Id asso connecte :" + selectedPack.getId());
		//packs = servicePack.rechercherPackAssociationParAssociation(selectedPack.getId());
		packs = servicePack.rechercherPackAssociationParAssociation(utilisateur.getId());
		
		System.out.println("taille packs: " + packs.size());
		System.out.println("contenue: " + packs.toString());
	}

	public String supprimerPack()
	{
		String suite=null;	
		servicePack.desactiverPack(13L);
		return suite;
	}

	public Personne getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Personne utilisateur) {
		this.utilisateur = utilisateur;
	}

	public IServicePack getServicePack() {
		return servicePack;
	}



	public void setServicePack(IServicePack servicePack) {
		this.servicePack = servicePack;
	}



	public List<PackAssociation> getPacks() {
		return packs;
	}



	public void setPacks(List<PackAssociation> packs) {
		this.packs = packs;
	}

}

	
	
	

