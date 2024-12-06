package pe.edu.cibertec.crud_I202313380.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    // Sobrescritura de equals y hashCode para consistencia
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return storeId != null && storeId.equals(store.storeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Método útil para agregar inventarios a la tienda
    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
        inventory.setStore(this);
    }

    // Método útil para eliminar inventarios de la tienda
    public void removeInventory(Inventory inventory) {
        inventories.remove(inventory);
        inventory.setStore(null);
    }
}
