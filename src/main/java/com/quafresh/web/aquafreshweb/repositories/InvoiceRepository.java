package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = """
    SELECT 
        i.ID AS invoice_id,
        i.invoice_date,
        i.total AS invoice_total,
        i.payment_method,
        i.payment_status,  -- Đã thay từ i.status thành i.payment_status  -- Giả sử bạn có cột này, nếu không thì thay thành cột chính xác của bạn

        o.ID AS order_id,
        o.order_date,
        o.total AS order_total,
        o.shipping_price,
        o.status AS order_status,

        u.fullname AS customer_name,
        u.email,
        u.phone_number AS phone_number,

        od.ID AS order_detail_id,
        od.quantity,
        od.price,

        pd.code_product_detail AS code_product_detail,
        p.product_name AS product_name

    FROM invoice i
    JOIN order_table o ON i.id_order = o.ID
    JOIN user u ON o.id_user = u.ID
    JOIN order_detail od ON od.id_order = o.ID
    JOIN product_detail pd ON pd.ID = od.id_product_detail
    JOIN product p ON p.ID = pd.id_product
""", nativeQuery = true)
    List<Object[]> getFullInvoiceList();

    // Truy vấn tìm hóa đơn theo id hóa đơn
    @Query(value = """
    SELECT 
        i.ID AS invoice_id,
        i.invoice_date,
        i.total AS invoice_total,
        i.payment_method,
        i.payment_status,  -- Đã thay từ i.status thành i.payment_status  -- Giả sử bạn có cột này, nếu không thì thay thành cột chính xác của bạn

        o.ID AS order_id,
        o.order_date,
        o.total AS order_total,
        o.shipping_price,
        o.status AS order_status,

        u.fullname AS customer_name,
        u.email,
        u.phone_number AS phone_number,

        od.ID AS order_detail_id,
        od.quantity,
        od.price,

        pd.code_product_detail AS code_product_detail,
        p.product_name AS product_name

    FROM invoice i
    JOIN order_table o ON i.id_order = o.ID
    JOIN user u ON o.id_user = u.ID
    JOIN order_detail od ON od.id_order = o.ID
    JOIN product_detail pd ON pd.ID = od.id_product_detail
    JOIN product p ON p.ID = pd.id_product
    WHERE i.ID = :invoiceId
    """, nativeQuery = true)
    List<Object[]> findInvoiceById(@Param("invoiceId") Integer invoiceId);
}
