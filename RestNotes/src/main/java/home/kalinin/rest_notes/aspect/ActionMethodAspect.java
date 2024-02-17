package home.kalinin.rest_notes.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class ActionMethodAspect {
    @Around("@annotation(home.kalinin.rest_notes.aspect.IActionMethod)")
        public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println("[1) @Around @IActionMethod] Время: " + executionTime + " ms; Что: " +joinPoint.getSignature().getName()+" - "+joinPoint.getSignature());
        return proceed;
    }
    @Before("execution(String home.kalinin.rest_notes.controller.RestNoteController.addNote(home.kalinin.rest_notes.model.Note))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        System.out.println("[2) @Before execution] Метод " + joinPoint.getSignature().getName() + " был вызван");
    }
}
