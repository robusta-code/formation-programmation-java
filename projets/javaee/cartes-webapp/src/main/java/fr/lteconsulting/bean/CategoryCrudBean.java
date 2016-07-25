package fr.lteconsulting.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.lteconsulting.dao.CategorieDao;
import fr.lteconsulting.model.Categorie;

@ManagedBean
@SessionScoped
public class CategoryCrudBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private CategorieDao dao = new CategorieDao();

	private Categorie editedItem;

	@PostConstruct
	public void init()
	{
		resetItem();
	}

	public List<Categorie> getCategories()
	{
		return dao.getCategories();
	}

	public Categorie getItem()
	{
		return editedItem;
	}

	public void edit( Categorie editedItem )
	{
		this.editedItem = editedItem;
	}

	public void resetItem()
	{
		editedItem = new Categorie();
	}

	public void valider()
	{
		if( editedItem.getId() == null )
			dao.addCategorie( editedItem );
		else
			dao.saveCategorie( editedItem );

		resetItem();
	}

	public boolean isEdition()
	{
		return editedItem != null && editedItem.getId() != null;
	}

	public void delete( Categorie categorie )
	{
		dao.delete( categorie.getId() );
	}
}