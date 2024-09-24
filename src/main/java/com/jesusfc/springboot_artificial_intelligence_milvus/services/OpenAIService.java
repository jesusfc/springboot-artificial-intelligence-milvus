package com.jesusfc.springboot_artificial_intelligence_milvus.services;


import com.jesusfc.springboot_artificial_intelligence_milvus.model.Answer;
import com.jesusfc.springboot_artificial_intelligence_milvus.model.CapitalRQ;
import com.jesusfc.springboot_artificial_intelligence_milvus.model.Question;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on may - 2024
 */
public interface OpenAIService {

    String getAnswer(String question);

    String getAnswer(Prompt prompt);

    Answer getAnswer(Question question);

    Answer getCapital(CapitalRQ capitalRQ);

    Answer getRagAnswer(Question question);
}
