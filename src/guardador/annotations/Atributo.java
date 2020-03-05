package guardador.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.Format;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Atributo {
  public String nome() default "";
  public Formatos formato() default Formatos.String;
}
