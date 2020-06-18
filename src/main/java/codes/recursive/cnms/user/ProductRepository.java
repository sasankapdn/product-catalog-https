package codes.recursive.cnms.user;

import codes.recursive.cnms.user.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class ProductRepository {

    @PersistenceContext
    @RequestScoped
    private static EntityManager entityManager;

    @Inject
    public ProductRepository(ProductProvider productProvider) {
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        configOverrides.put("hibernate.connection.url", productProvider.getDbUrl());
        configOverrides.put("hibernate.connection.username", productProvider.getDbUser());
        configOverrides.put("hibernate.connection.password", productProvider.getDbPassword());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU", configOverrides);
        entityManager = emf.createEntityManager();
    }

    public Set<ConstraintViolation<Product>> validate(Product product) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        return constraintViolations;
    }

    public Product save(Product product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
        return product;
    }

    public Product get(String id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }

    public List<Product> findAll() {
        return entityManager.createQuery("from Product").getResultList();
    }

    public List<Product> findAll(int offset, int max) {
        Query query = entityManager.createQuery("from Product");
        query.setFirstResult(offset);
        query.setMaxResults(max);
        return query.getResultList();
    }

    public long count() {
        Query queryTotal = entityManager.createQuery("Select count(u.id) from Product u");
        long countResult = (long)queryTotal.getSingleResult();
        return countResult;
    }

    public void deleteById(String id) {
        // Retrieve the movie with this ID
        Product product = get(id);
        if (product != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(product);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Product create(RequestBody requestBody) {
        Product prod = new Product();
        prod.list_price=requestBody.LIST_PRICE;
        prod.product_id =requestBody.PRODUCT_ID;
        prod.product_name=requestBody.PRODUCT_NAME;
        prod.product_status="Available";
        prod.min_price="2.99";
        prod.created_by="0";
        prod.last_updated_by="0";
        prod.object_version_id="1";
        entityManager.getTransaction().begin();
        entityManager.merge(prod);
        entityManager.getTransaction().commit();
        return prod;
    }


}
