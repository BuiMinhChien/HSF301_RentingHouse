package com.spring.mvc.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class) // Liên kết với class validator
//@Constraint: Đánh dấu đây là một constraint (ràng buộc) tùy chỉnh, được sử dụng để validate dữ liệu.
//validatedBy = DateValidator.class: Xác định DateValidator là class sẽ thực hiện việc kiểm tra tính hợp lệ cho các trường được đánh dấu bằng @ValidDate. Class này sẽ chứa logic để kiểm tra xem dữ liệu có đúng định dạng ngày hay không.
@Target({ElementType.FIELD, ElementType.METHOD}) // Áp dụng cho field và method
//@Target: Chỉ định các loại đối tượng mà annotation này có thể áp dụng.
//ElementType.FIELD: Annotation này có thể áp dụng cho các trường (field) của class.
//ElementType.METHOD: Annotation này có thể áp dụng cho các phương thức (method).
@Retention(RetentionPolicy.RUNTIME) // Lưu giữ thông tin annotation tại runtime
//@Retention: Chỉ định thời gian lưu giữ (retention) của annotation.
//RetentionPolicy.RUNTIME: Annotation sẽ được lưu giữ tại runtime, nghĩa là có thể truy cập thông qua reflection khi ứng dụng đang chạy. Điều này cần thiết cho các annotation validator vì chúng cần được kiểm tra khi ứng dụng thực thi.
public @interface ValidDate {
//@interface: Đây là cú pháp để khai báo một annotation tùy chỉnh. Trong Java, khi khai báo một annotation mới, cần sử dụng @interface thay vì class hoặc interface.
//ValidDate: Tên của annotation mới. Bạn có thể sử dụng @ValidDate để đánh dấu các trường hoặc phương thức cần kiểm tra tính hợp lệ về ngày tháng.

    String message() default "Ngày không hợp lệ. Định dạng phải là dd/MM/yyyy"; // Thông báo lỗi

    Class<?>[] groups() default {}; // Cho phép phân loại validator theo nhóm
    //groups(): Cho phép phân loại validator theo nhóm. Đây là tính năng của Jakarta Validation API, giúp nhóm các ràng buộc lại và áp dụng validator theo từng nhóm. Mặc định không có nhóm nào được áp dụng.

    Class<? extends Payload>[] payload() default {}; // Cung cấp thêm thông tin
    //payload(): Cung cấp thêm thông tin meta cho constraint. Thường được sử dụng cho các framework để mang thêm dữ liệu về ràng buộc nhưng ít được dùng trong thực tế.

    String pattern() default "dd/MM/yyyy"; // Cho phép chỉ định định dạng
    //pattern(): Cho phép chỉ định định dạng ngày mà validator sẽ sử dụng
}
