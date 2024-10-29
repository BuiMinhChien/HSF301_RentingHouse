package com.spring.mvc.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NumberValidator.class)
//@Constraint: Đánh dấu @ValidNumber là một constraint (ràng buộc) tùy chỉnh, sử dụng để kiểm tra tính hợp lệ.
//validatedBy = NumberValidator.class: Xác định class NumberValidator sẽ thực hiện logic kiểm tra tính hợp lệ cho các trường được đánh dấu với @ValidNumber.
@Target({ElementType.FIELD, ElementType.METHOD})
//@Target: Chỉ định loại đối tượng mà annotation có thể áp dụng.
//ElementType.FIELD: Có thể áp dụng cho các trường (field) trong class.
//ElementType.METHOD: Có thể áp dụng cho các phương thức (method) trong class.
@Retention(RetentionPolicy.RUNTIME)
//@Retention: Quy định thời gian tồn tại của annotation.
//RetentionPolicy.RUNTIME: Annotation sẽ được giữ lại tại runtime, để có thể kiểm tra và xử lý khi ứng dụng đang chạy
public @interface ValidNumber {
//@interface: Đây là cú pháp để khai báo một annotation tùy chỉnh. Trong Java, khi khai báo một annotation mới, cần sử dụng @interface thay vì class hoặc interface.
//ValidNumber: Tên của annotation mới. Bạn có thể sử dụng @ValidNumber để đánh dấu các trường hoặc phương thức cần kiểm tra tính hợp lệ về số.

    int min() default Integer.MIN_VALUE;
    //Xác định giá trị nhỏ nhất hợp lệ cho số được kiểm tra.
    //default Integer.MIN_VALUE: Nếu không đặt giá trị min cụ thể, giá trị nhỏ nhất hợp lệ sẽ là Integer.MIN_VALUE (giới hạn nhỏ nhất của kiểu int).
    int max() default Integer.MAX_VALUE;
    //Xác định giá trị lớn nhất hợp lệ cho số được kiểm tra.
    //default Integer.MAX_VALUE: Nếu không đặt giá trị max cụ thể, giá trị lớn nhất hợp lệ sẽ là Integer.MAX_VALUE (giới hạn lớn nhất của kiểu int).
    String message() default "Số không hợp lệ, số phải có định dạng ##.##";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
//Giải thích lý do sử dụng () sau tên biến. VD: int min() default Integer.MIN_VALUE;
//Cú pháp của annotation: Trong Java, các thuộc tính của annotation được định nghĩa như các phương thức trong interface. Đó là lý do chúng có dấu (), giống như cách bạn khai báo một phương thức.
//Không có dấu () sẽ gây lỗi: Nếu không sử dụng () sau tên thuộc tính, trình biên dịch Java sẽ hiểu nhầm rằng bạn đang khai báo một biến, dẫn đến lỗi cú pháp vì Java yêu cầu khai báo các thuộc tính của annotation là các phương thức.