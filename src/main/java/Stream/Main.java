package Stream;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;
import model.Customer;
import model.Order;
import model.Product;

public class Main {
    public static void main(String[] args) {

        Customer shinjiIkari = new Customer(1L, "Shinji Ikari", Integer.valueOf(1));
        Customer asukaLangley = new Customer(2L, "Asuka Langley", Integer.valueOf(2));

        Product manga = new Product(1L, "Manga", "Libri", Double.valueOf(10));
        Product robot = new Product(2L, "Robot", "Elettronica", Double.valueOf(850));
        Product tuta = new Product(3L, "Tuta", "Abbigliamento", Double.valueOf(85));

        List<Order> orders = List.of(
                new Order(1L, "in lavorazione", LocalDate.now(), LocalDate.now().plusDays(3), List.of(manga, tuta), shinjiIkari),
                new Order(2L, "spedito", LocalDate.now().minusDays(1), LocalDate.now().plusDays(2), List.of(robot), asukaLangley),
                new Order(3L, "spedito", LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), List.of(tuta, tuta, manga), asukaLangley)
        );

        List<Product> products = List.of(manga, robot, tuta);

        Map<Customer, List<Order>> ordiniPerCliente = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        System.out.println("Esercizio 1 - Ordini per cliente:");
        ordiniPerCliente.forEach((cliente, listaOrdini) -> {
            System.out.println(cliente.getName() + ": " + listaOrdini.size() + " ordini");
        });

        System.out.println("---------------");

        Map<Customer, Double> totalePerCliente = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.summingDouble(order ->
                                order.getProducts().stream()
                                        .mapToDouble(p -> p.getPrice().doubleValue())
                                        .sum())
                ));

        System.out.println("Esercizio 2 - Totale vendite per cliente:");
        totalePerCliente.forEach((cliente, totale) -> {
            System.out.println(cliente.getName() + ": " + totale + "€");
        });

        System.out.println("---------------");

        List<Product> topProdotti = products.stream()
                .sorted(Comparator.comparing(
                        (Product p) -> p.getPrice()
                ).reversed())

                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Esercizio 3 - Prodotti più costosi:");
        topProdotti.forEach(p -> System.out.println(p.getName() + " - " + p.getPrice() + "€"));

        System.out.println("---------------");

        double mediaOrdini = orders.stream()
                .mapToDouble(order ->
                        order.getProducts().stream()
                                .mapToDouble(p -> p.getPrice().doubleValue())
                                .sum())
                .average()
                .orElse(0.0);

        System.out.println("Esercizio 4 - Media importi ordini: " + mediaOrdini + "€");

        System.out.println("---------------");

        Map<String, Double> sommaPerCategoria = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.summingDouble(p -> p.getPrice().doubleValue())
                ));

        System.out.println("Esercizio 5 - Somma importi per categoria:");
        sommaPerCategoria.forEach((categoria, totale) -> {
            System.out.println(categoria + ": " + totale + "€");
        });


    }
}

