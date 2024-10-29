package com.spring.mvc.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<ValidNumber, String> {
//NumberValidator: Thực hiện ConstraintValidator<ValidNumber, String> để chỉ định rằng đây là validator cho annotation @ValidNumber, với kiểu dữ liệu cần kiểm tra là String.

    private int min;
    private int max;
    private String message;
    @Override
    public void initialize(ValidNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String inputNumber, ConstraintValidatorContext validatorContext) {
        if (inputNumber == null) {
            return true;
        }
        //Kiểm tra null: Nếu inputNumber là null, phương thức trả về true, tức là bỏ qua kiểm tra (có thể kiểm tra null riêng bằng @NotNull nếu cần).
        try {
            // Sử dụng ResourceBundle để lấy thông báo lỗi
            if (message != null && message.contains("{")) {
                validatorContext.disableDefaultConstraintViolation();
                validatorContext.buildConstraintViolationWithTemplate(message)
                        .addConstraintViolation();
            }
            //Nếu message có chứa ký hiệu {, tức là thông báo này có thể là một key từ file messages.properties,
            // thì vô hiệu hóa thông báo lỗi mặc định (validatorContext.disableDefaultConstraintViolation())
            // và xây dựng một thông báo tùy chỉnh dựa trên message.

            int number = Integer.parseInt(inputNumber);
            //Integer.parseInt(inputNumber): Chuyển chuỗi inputNumber thành một số nguyên (int). Nếu thành công, inputNumber là một chuỗi số hợp lệ.
            return number >= min && number <= max;
        } catch (Exception exception) {
            exception.printStackTrace();
            //Nếu có ngoại lệ xảy ra (ví dụ: inputNumber không phải là một chuỗi số),
            // ngoại lệ sẽ được in ra và trả về false, nghĩa là inputNumber không hợp lệ.
        }
        return false;
    }
}
