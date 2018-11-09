package fr.afcepf.al32.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="typePack",
                     discriminatorType=DiscriminatorType.STRING)
@Table(name="Pack")
@NamedQueries({
	@NamedQuery(name="PackAssociationfindAll", query="SELECT c FROM PackAssociation c"),
	
	@NamedQuery(name="PackAssociation.findAllByType", query="SELECT c FROM PackAssociation c "
																+ "WHERE c.typeProduit.id = :idType "
																+ " and c.dateRetrait is null"),
	
	@NamedQuery(name="PackAssociation.findAllByAssociation", query="SELECT c FROM PackAssociation c "
																	+ "WHERE c.association.id = :id "
																	+ " and c.dateRetrait is null"),
	
	@NamedQuery(name="PackAssociation.findAllByAssociationAndType", query="SELECT c FROM PackAssociation c "
																		+ "WHERE c.typeProduit.id = :idType "
																		+ "And c.association.id = :id "
																		+ " and c.dateRetrait is null")
})
public abstract class Pack 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String libelle;
	
	private Double prix;
	
	@ManyToOne
	@JoinColumn(name="idTypeProduit")
	private TypeProduit typeProduit;
	
	@ManyToMany(cascade = { 
//	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	@JoinTable(name="Pack_Produit",
    joinColumns= {@JoinColumn(name="idPack")},
    inverseJoinColumns = {@JoinColumn(name="idProduit")})
	private List<Produit> produits;
	
	@ManyToMany(mappedBy="packs")//LAZY par defaut
	private List<Don> dons;
	
    public Pack(String libelle, Double prix) {
		
		this.libelle = libelle;
		this.prix = prix;
		
	}
  
    public Pack(String libelle, Double prix, TypeProduit typeProduit, List<Produit> produits) {
		
		this.libelle = libelle;
		this.prix = prix;
		this.typeProduit = typeProduit;
		this.produits = produits;
	}

}
