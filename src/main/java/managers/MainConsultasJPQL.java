package managers;

import funciones.FuncionApp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.example.Articulo;
import org.example.Cliente;
import org.example.Factura;
import org.example.FacturaDetalle;

public class MainConsultasJPQL {

    public static void main(String[] args) {
        // 1) Primero cargamos datos mínimos en la BD en memoria
        //    para que las consultas JPQL tengan algo real que mostrar.
        precargarDatosDemo();

        // 2) Después ejecutamos en bloque todos los ejercicios del TP,
        //    uno atrás del otro, para imprimir los resultados en consola.
        ejecutarEjerciciosTP();
    }

    // Este método simplemente llama a todos los ejercicios en orden.
    // La idea es que cuando corres el main, te imprima todo lo que pide el práctico.
    private static void ejecutarEjerciciosTP() {
        // Ejercicio 1: Listar todos los clientes
        ejercicio1_listarTodosLosClientes();

        // Ejercicio 2: Listar todas las facturas del último mes
        ejercicio2_facturasUltimoMes();

        // Ejercicio 3: Cliente que generó más facturas
        ejercicio3_clienteConMasFacturas();

        // Ejercicio 4: Artículos más vendidos (sumatoria de cantidad)
        ejercicio4_articulosMasVendidos();

        // Ejercicio 5: Facturas últimos 3 meses de un cliente específico (uso ID 1L como demo)
        ejercicio5_facturasUltimosTresMesesDeCliente(1L);

        // Ejercicio 6: Total facturado por un cliente (ID 1L como demo)
        ejercicio6_totalFacturadoPorCliente(1L);

        // Ejercicio 7: Artículos vendidos en una factura específica (uso ID 1L como demo)
        ejercicio7_articulosDeFactura(1L);

        // Ejercicio 8: Artículo más caro vendido en una factura específica (uso ID 1L como demo)
        ejercicio8_articuloMasCaroEnFactura(1L);

        // Ejercicio 9: Cantidad total de facturas generadas en el sistema
        ejercicio9_contarFacturas();

        // Ejercicio 10: Facturas cuyo total es mayor a un valor
        ejercicio10_facturasConTotalMayorA(200.0);

        // Ejercicio 11: Facturas que contienen un artículo filtrado por nombre parcial
        ejercicio11_facturasQueContienenArticuloPorNombre("manzana");

        // Ejercicio 12: Artículos cuyo código matchea parcial
        ejercicio12_articulosPorCodigoParcial("PE"); // ejemplo "PE" para que matchee "PERA"

        // Ejercicio 13: Artículos cuyo precio es mayor al promedio
        ejercicio13_articulosMasCarosQueElPromedio();

        // Ejercicio 14: Ejemplo de EXISTS (clientes con al menos una factura activa)
        ejercicio14_clientesConFacturasActivas();

        // Además, seguimos pudiendo usar todos los métodos que dio la cátedra
        // como buscarClientesXRazonSocialParcial(), etc., si querés probarlos.
    }

    // ===================== MÉTODOS ORIGINALES =====================

    public static void buscarFacturas() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            List<Factura> facturas = mFactura.getFacturas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturasActivas() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            List<Factura> facturas = mFactura.getFacturasActivas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturasXNroComprobante() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            List<Factura> facturas = mFactura.getFacturasXNroComprobante(796910L);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturasXRangoFechas() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarSeisMeses(fechaActual);
            List<Factura> facturas = mFactura.buscarFacturasXRangoFechas(fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturaXPtoVentaXNroComprobante() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            Factura factura = mFactura.getFacturaXPtoVentaXNroComprobante(2024, 796910L);
            mostrarFactura(factura);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturasXCliente() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            List<Factura> facturas = mFactura.getFacturasXCliente(7L);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturasXCuitCliente() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            List<Factura> facturas = mFactura.getFacturasXCuitCliente("27236068981");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarFacturasXArticulo() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            List<Factura> facturas = mFactura.getFacturasXArticulo(6L);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void mostrarMaximoNroFactura() {
        FacturaManager mFactura = new FacturaManager(true);

        try {
            Long nroCompMax = mFactura.getMaxNroComprobanteFactura();
            System.out.println("N° " + nroCompMax);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }

    }

    public static void buscarClientesXIds() {
        ClienteManager mCliente = new ClienteManager(true);

        try {
            List<Long> idsClientes = new ArrayList();
            idsClientes.add(1L);
            idsClientes.add(2L);

            for(Cliente cli : mCliente.getClientesXIds(idsClientes)) {
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }

    }

    public static void buscarClientesXRazonSocialParcial() {
        ClienteManager mCliente = new ClienteManager(true);

        try {
            List<Long> idsClientes = new ArrayList();
            idsClientes.add(1L);
            idsClientes.add(2L);

            for(Cliente cli : mCliente.getClientesXRazonSocialParcialmente("Lui")) {
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }

    }

    public static void mostrarFactura(Factura factura) {
        List<Factura> facturas = new ArrayList();
        facturas.add(factura);
        mostrarFacturas(facturas);
    }

    public static void mostrarFacturas(List<Factura> facturas) {
        for(Factura fact : facturas) {
            System.out.println("N° Comp: " + fact.getStrProVentaNroComprobante());
            System.out.println("Fecha: " + FuncionApp.formatLocalDateToString(fact.getFechaComprobante()));
            System.out.println("CUIT Cliente: " + FuncionApp.formatCuitConGuiones(fact.getCliente().getCuit()));
            System.out.println("Cliente: " + fact.getCliente().getRazonSocial() + " (" + fact.getCliente().getId() + ")");
            System.out.println("------Articulos------");

            for(FacturaDetalle detalle : fact.getDetallesFactura()) {
                System.out.println(detalle.getArticulo().getDenominacion() + ", " + detalle.getCantidad() + " unidades, $" + FuncionApp.getFormatMilDecimal(detalle.getSubTotal(), 2));
            }

            System.out.println("Total: $" + FuncionApp.getFormatMilDecimal(fact.getTotal(), 2));
            System.out.println("*************************");
        }

    }

    // ===================== CONSULTAS PEDIDAS EN EL TP =====================
    // Cada método que sigue corresponde a un ejercicio del práctico.
    // No son SQL nativos, son JPQL (opera sobre entidades).

    // Ejercicio 1:
    // "Listar todos los clientes registrados en el sistema."
    private static void ejercicio1_listarTodosLosClientes() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT c FROM Cliente c";
            Query query = em.createQuery(jpql);
            List<Cliente> clientes = query.getResultList();

            System.out.println("=== EJ1: Todos los clientes ===");
            for (Cliente c : clientes) {
                System.out.println("Cliente: " + c.getId() + " - " + c.getRazonSocial() + " CUIT " + c.getCuit());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 2:
    // "Listar todas las facturas generadas en el último mes."
    private static void ejercicio2_facturasUltimoMes() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            LocalDate hoy = LocalDate.now();
            LocalDate haceUnMes = hoy.minusMonths(1);

            String jpql = "SELECT f FROM Factura f " +
                    "WHERE f.fechaComprobante >= :fechaLimite " +
                    "ORDER BY f.fechaComprobante DESC";

            Query query = em.createQuery(jpql);
            query.setParameter("fechaLimite", haceUnMes);

            List<Factura> facturas = query.getResultList();

            System.out.println("=== EJ2: Facturas último mes ===");
            for (Factura f : facturas) {
                System.out.println("Factura " + f.getStrProVentaNroComprobante() +
                        " Fecha " + f.getFechaComprobante() +
                        " Total $" + f.getTotal());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 3:
    // "Obtener el cliente que ha generado más facturas."
    private static void ejercicio3_clienteConMasFacturas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT f.cliente, COUNT(f) " +
                            "FROM Factura f " +
                            "GROUP BY f.cliente " +
                            "ORDER BY COUNT(f) DESC";

            Query query = em.createQuery(jpql);
            query.setMaxResults(1); // top 1

            Object[] row = (Object[]) query.getSingleResult();
            Cliente cliente = (Cliente) row[0];
            Long cantidad = (Long) row[1];

            System.out.println("=== EJ3: Cliente con más facturas ===");
            System.out.println("Cliente: " + cliente.getRazonSocial() +
                    " (id=" + cliente.getId() + ") - Cantidad facturas: " + cantidad);
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 4:
    // "Listar los artículos más vendidos ordenados por la cantidad total vendida."
    private static void ejercicio4_articulosMasVendidos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT d.articulo, SUM(d.cantidad) " +
                            "FROM FacturaDetalle d " +
                            "GROUP BY d.articulo " +
                            "ORDER BY SUM(d.cantidad) DESC";

            Query query = em.createQuery(jpql);
            List<Object[]> resultados = query.getResultList();

            System.out.println("=== EJ4: Artículos más vendidos ===");
            for (Object[] fila : resultados) {
                Articulo art = (Articulo) fila[0];
                Number cantTotal = (Number) fila[1];
                System.out.println("Artículo: " + art.getDenominacion() +
                        " | Cantidad total vendida: " + cantTotal);
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 5:
    // "Consultar las facturas emitidas en los 3 últimos meses de un cliente específico."
    private static void ejercicio5_facturasUltimosTresMesesDeCliente(Long idCliente) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            LocalDate hoy = LocalDate.now();
            LocalDate limite = hoy.minusMonths(3);

            String jpql =
                    "SELECT f FROM Factura f " +
                            "WHERE f.cliente.id = :idCliente " +
                            "AND f.fechaComprobante >= :fechaLimite " +
                            "ORDER BY f.fechaComprobante DESC";

            Query query = em.createQuery(jpql);
            query.setParameter("idCliente", idCliente);
            query.setParameter("fechaLimite", limite);

            List<Factura> facturas = query.getResultList();

            System.out.println("=== EJ5: Facturas últimos 3 meses del cliente " + idCliente + " ===");
            for (Factura f : facturas) {
                System.out.println("Factura " + f.getStrProVentaNroComprobante() +
                        " Fecha " + f.getFechaComprobante() +
                        " Total $" + f.getTotal());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 6:
    // "Calcular el monto total facturado por un cliente."
    private static void ejercicio6_totalFacturadoPorCliente(Long idCliente) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT SUM(f.total) " +
                            "FROM Factura f " +
                            "WHERE f.cliente.id = :idCliente";

            Query query = em.createQuery(jpql);
            query.setParameter("idCliente", idCliente);

            Double totalFacturado = (Double) query.getSingleResult();

            System.out.println("=== EJ6: Total facturado por cliente " + idCliente + " ===");
            System.out.println("Total facturado = $" + totalFacturado);
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 7:
    // "Listar los artículos vendidos en una factura específica (por id de factura)."
    private static void ejercicio7_articulosDeFactura(Long idFactura) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT DISTINCT d.articulo " +
                            "FROM FacturaDetalle d " +
                            "WHERE d.factura.id = :idFactura";

            Query query = em.createQuery(jpql);
            query.setParameter("idFactura", idFactura);

            List<Articulo> articulos = query.getResultList();

            System.out.println("=== EJ7: Artículos en la factura " + idFactura + " ===");
            for (Articulo a : articulos) {
                System.out.println("Artículo vendido: " + a.getDenominacion() +
                        " | Precio: $" + a.getPrecioVenta());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 8:
    // "Obtener el artículo más caro vendido en una factura específica."
    private static void ejercicio8_articuloMasCaroEnFactura(Long idFactura) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT d.articulo " +
                            "FROM FacturaDetalle d " +
                            "WHERE d.factura.id = :idFactura " +
                            "ORDER BY d.articulo.precioVenta DESC";

            Query query = em.createQuery(jpql);
            query.setParameter("idFactura", idFactura);
            query.setMaxResults(1); // nos quedamos con el más caro

            Articulo articuloMasCaro = (Articulo) query.getSingleResult();

            System.out.println("=== EJ8: Artículo más caro en factura " + idFactura + " ===");
            System.out.println("Artículo: " + articuloMasCaro.getDenominacion() +
                    " | Precio $" + articuloMasCaro.getPrecioVenta());
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 9:
    // "Contar la cantidad total de facturas generadas en el sistema."
    private static void ejercicio9_contarFacturas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Factura f";

            Query query = em.createQuery(jpql);
            Long totalFacturas = (Long) query.getSingleResult();

            System.out.println("=== EJ9: Cantidad total de facturas ===");
            System.out.println("Total de facturas emitidas: " + totalFacturas);
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 10:
    // "Listar las facturas cuyo total es mayor a un valor determinado."
    private static void ejercicio10_facturasConTotalMayorA(Double montoMinimo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT f FROM Factura f " +
                            "WHERE f.total > :montoMinimo " +
                            "ORDER BY f.total DESC";

            Query query = em.createQuery(jpql);
            query.setParameter("montoMinimo", montoMinimo);

            List<Factura> facturas = query.getResultList();

            System.out.println("=== EJ10: Facturas con total > " + montoMinimo + " ===");
            for (Factura f : facturas) {
                System.out.println("Factura " + f.getStrProVentaNroComprobante() +
                        " Total $" + f.getTotal());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 11:
    // "Consultar las facturas que contienen un artículo específico filtrando por nombre del artículo."
    private static void ejercicio11_facturasQueContienenArticuloPorNombre(String nombreArticuloParcial) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT DISTINCT d.factura " +
                            "FROM FacturaDetalle d " +
                            "WHERE LOWER(d.articulo.denominacion) LIKE CONCAT('%', LOWER(:nombre), '%')";

            Query query = em.createQuery(jpql);
            query.setParameter("nombre", nombreArticuloParcial);

            List<Factura> facturas = query.getResultList();

            System.out.println("=== EJ11: Facturas que contienen artículo que matchea \"" + nombreArticuloParcial + "\" ===");
            for (Factura f : facturas) {
                System.out.println("Factura " + f.getStrProVentaNroComprobante() +
                        " Cliente " + f.getCliente().getRazonSocial());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 12:
    // "Listar los artículos filtrando por código parcial."
    private static void ejercicio12_articulosPorCodigoParcial(String codigoParcial) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT a FROM Articulo a " +
                            "WHERE a.codigo LIKE :codigoParcial";

            Query query = em.createQuery(jpql);
            query.setParameter("codigoParcial", "%" + codigoParcial + "%");

            List<Articulo> articulos = query.getResultList();

            System.out.println("=== EJ12: Artículos cuyo código contiene \"" + codigoParcial + "\" ===");
            for (Articulo a : articulos) {
                System.out.println("[" + a.getCodigo() + "] " + a.getDenominacion()
                        + " - $" + a.getPrecioVenta());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 13:
    // "Listar todos los artículos cuyo precio sea mayor que el promedio de los precios."
    private static void ejercicio13_articulosMasCarosQueElPromedio() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT a " +
                            "FROM Articulo a " +
                            "WHERE a.precioVenta > (" +
                            "   SELECT AVG(a2.precioVenta) " +
                            "   FROM Articulo a2" +
                            ") " +
                            "ORDER BY a.precioVenta DESC";

            Query query = em.createQuery(jpql);
            List<Articulo> articulos = query.getResultList();

            System.out.println("=== EJ13: Artículos con precio > promedio ===");
            for (Articulo a : articulos) {
                System.out.println(a.getDenominacion() +
                        " | $" + a.getPrecioVenta());
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Ejercicio 14:
    // "Explique y ejemplifique la cláusula EXISTS."
    // Traer clientes que tengan al menos UNA factura activa.
    private static void ejercicio14_clientesConFacturasActivas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        try {
            String jpql =
                    "SELECT c " +
                            "FROM Cliente c " +
                            "WHERE EXISTS (" +
                            "   SELECT f " +
                            "   FROM Factura f " +
                            "   WHERE f.cliente = c " +
                            "     AND f.fechaBaja IS NULL" +
                            ")";

            Query query = em.createQuery(jpql);

            List<Cliente> clientes = query.getResultList();

            System.out.println("=== EJ14: Clientes con al menos una factura activa ===");
            for (Cliente c : clientes) {
                System.out.println("Cliente " + c.getRazonSocial() + " (id=" + c.getId() + ")");
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // ===========================================
    // Cargar datos base en la BD en memoria (H2)
    // ===========================================
    private static void precargarDatosDemo() {
        // Esta función mete una UnidadMedida, un par de Artículos, un Cliente,
        // una Factura con Detalles. Así las consultas tienen algo que devolver.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Unidad de medida
            org.example.UnidadMedida unidadKg = org.example.UnidadMedida.builder()
                    .denominacion("Kg")
                    .build();
            em.persist(unidadKg);

            // Artículos de ejemplo
            org.example.Articulo artManzana = org.example.ArticuloInsumo.builder()
                    .codigo("MANZ")
                    .denominacion("Manzana")
                    .precioVenta(5.0)
                    .unidadMedida(unidadKg)
                    .precioCompra(1.5)
                    .stockActual(100)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .build();

            org.example.Articulo artPera = org.example.ArticuloInsumo.builder()
                    .codigo("PERA")
                    .denominacion("Pera")
                    .precioVenta(10.0)
                    .unidadMedida(unidadKg)
                    .precioCompra(2.5)
                    .stockActual(130)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .build();

            em.persist(artManzana);
            em.persist(artPera);

            // Cliente demo
            org.example.Cliente clienteDemo = org.example.Cliente.builder()
                    .cuit(FuncionApp.generateRandomCUIT())
                    .razonSocial("Cliente Demo")
                    .build();
            em.persist(clienteDemo);

            // Detalles de factura
            org.example.FacturaDetalle det1 = new org.example.FacturaDetalle(2.0, artManzana);
            det1.calcularSubTotal();

            org.example.FacturaDetalle det2 = new org.example.FacturaDetalle(1.0, artPera);
            det2.calcularSubTotal();

            // Factura demo
            org.example.Factura facturaDemo = org.example.Factura.builder()
                    .puntoVenta(2024)
                    .fechaComprobante(LocalDate.now().minusDays(10))
                    .cliente(clienteDemo)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();

            facturaDemo.addDetalleFactura(det1);
            facturaDemo.addDetalleFactura(det2);
            facturaDemo.calcularTotal();

            em.persist(facturaDemo);

            em.getTransaction().commit();

            System.out.println(">>> Datos demo cargados correctamente en memoria.");
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}