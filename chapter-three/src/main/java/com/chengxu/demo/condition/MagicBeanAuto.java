package com.chengxu.demo.condition;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(MagicExistsCondition.class)
public class MagicBeanAuto {
}
