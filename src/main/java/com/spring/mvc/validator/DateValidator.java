package com.spring.mvc.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateValidator implements ConstraintValidator<ValidDate, String> {
//DateValidator: Là một validator để kiểm tra tính hợp lệ của ngày tháng.
//implements ConstraintValidator<ValidDate, String>: ConstraintValidator là một interface của Jakarta Validation, giúp định nghĩa logic để kiểm tra tính hợp lệ của các ràng buộc. Tham số ValidDate là annotation tùy chỉnh, và String là kiểu dữ liệu sẽ được kiểm tra (ở đây là chuỗi ngày tháng).

    private String datePattern;
    private String message;
    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation); //Được gọi khi validator được khởi tạo, cho phép lấy các giá trị từ annotation @ValidDate.
        this.datePattern = constraintAnnotation.pattern(); // Lấy định dạng từ annotation
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
    //String date: Chuỗi ngày tháng cần kiểm tra tính hợp lệ.
    //ConstraintValidatorContext context: Cung cấp ngữ cảnh để tuỳ chỉnh thông báo lỗi khi validation thất bại, chẳng hạn như để thay thế thông báo lỗi mặc định bằng một thông báo lỗi khác.

        // Nếu giá trị null thì bỏ qua validation (có thể dùng @NotNull để kiểm tra null)
        if (date == null) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(datePattern); //Sử dụng SimpleDateFormat với datePattern để định dạng ngày.
        sdf.setLenient(false); // Đảm bảo rằng không chấp nhận ngày không hợp lệ như 30/02

        try {
            //Kiem tra neu message is a key
            if (message != null && message.contains("{")) {
                //disable thông báo lỗi mặc định để sử dụng message tùy chỉnh
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addConstraintViolation();
            }
            // Kiểm tra tính hợp lệ của chuỗi
            sdf.parse(date); //Cố gắng chuyển chuỗi date thành một đối tượng Date. Nếu thành công, nghĩa là chuỗi date hợp lệ.
            return true;
        } catch (ParseException e) {
            // Nếu có lỗi, nghĩa là chuỗi không khớp với định dạng
            return false;
        }
    }
}
