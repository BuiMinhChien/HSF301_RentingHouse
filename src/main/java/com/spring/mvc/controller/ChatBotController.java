package com.spring.mvc.controller;

import com.spring.mvc.dto.MainTopicDTO;
import com.spring.mvc.dto.QuestionDTO;
import com.spring.mvc.dto.SubTopicDTO;
import com.spring.mvc.entity.Question;
import com.spring.mvc.entity.Topic;
import com.spring.mvc.service.QuestionService;
import com.spring.mvc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatBotController {

    private final TopicService topicService;
    private final QuestionService questionService;
//    private final StaffService staffService;

    @Autowired
    public ChatBotController(TopicService topicService, QuestionService questionService) {
        this.topicService = topicService;
        this.questionService = questionService;
//        this.staffService = staffService;
    }

    @GetMapping("/topics/sub/{parentId}")
    public Map<String, Object> getSubTopicsOrQuestions(@PathVariable("parentId") int parentId) {

        Map<String, Object> response = new HashMap<>();
        Topic topic = topicService.getTopicById(parentId);

        if (topic.getTopic_name().equals("Direct Support")) {
            response.put("type", "Direct Support");
            response.put("data", "temp");
        } else {
            // Lấy danh sách các sub-topic dựa trên parentId
            System.out.println(parentId);
            List<Topic> subTopics = topicService.getSubTopics(parentId);

            if (!subTopics.isEmpty()) {
                response.put("type", "topics");
                response.put("data", subTopics);
            } else {
                // Nếu không có sub-topic, lấy các câu hỏi liên quan đến topic đó
                List<Question> questions = questionService.getQuestionsByTopic(parentId);
                response.put("type", "questions");
                response.put("data", questions);
            }
        }
        return response;
    }

    @GetMapping("/topics")
    public List<Topic> getMainTopics() {
        return topicService.getMainTopics();
    }

    @PutMapping("/topics/update/{id}")
    public ResponseEntity<String> updateTopicName(@PathVariable("id") int id, @RequestBody Map<String, String> request) {
        String newTopicName = request.get("topic_name");
        System.out.println(newTopicName);
        boolean updated = topicService.updateTopicName(id, newTopicName);
        if (updated) {
            return ResponseEntity.ok("Topic name updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found");
        }
    }

    @DeleteMapping("/topics/delete/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable("id") int id) {
        topicService.deleteTopic(id);
        return  ResponseEntity.ok("Topic deleted successfully");
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Map<String, Object>> viewDetails(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Topic topic = topicService.getTopicById(id);

            if (topic != null) {
                List<Topic> subTopics = topicService.getSubTopics(id);
                if (!subTopics.isEmpty()) {
                    response.put("type", "topics");
                    response.put("data", subTopics);
                } else {
                    List<Question> questions = questionService.getQuestionsByTopic(id);
                    response.put("type", "questions");
                    response.put("data", questions != null ? questions : Collections.emptyList());
                }
                return ResponseEntity.ok(response);
            }

            response.put("error", "Topic not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Ghi log lỗi để kiểm tra chi tiết
            e.printStackTrace();
            response.put("error", "Internal Server Error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/questions/update/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable("questionId") int questionId, @RequestBody Map<String, String> request) {
        String newQuestion = request.get("question");
        String newAnswer = request.get("answer");
        boolean updated = questionService.updateQuestion(questionId, newQuestion, newAnswer);
        if (updated) {
            return ResponseEntity.ok("Question updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
    }

    @DeleteMapping("/questions/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") int questionId) {
        questionService.deleteQuestion(questionId);
        return  ResponseEntity.ok("Question deleted successfully");
    }

    @PostMapping("/insert/mainTopic")
    public ResponseEntity<String> insertMainTopic(@RequestBody MainTopicDTO mainTopicDTO){
        if (topicService.saveMainTopic(mainTopicDTO.getTopicName())) {
            return ResponseEntity.ok("Topic saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/insert/subTopic")
    public ResponseEntity<String> insertSubTopic(@RequestBody SubTopicDTO subTopicDTO){
        if (topicService.saveSubTopic(subTopicDTO.getMainTopicId(), subTopicDTO.getTopicName())) {
            return ResponseEntity.ok("Topic saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/insert/question")
    public ResponseEntity<String> insertQuestion(@RequestBody QuestionDTO questionDTO){
        if (questionService.saveQuestion(questionDTO.getSubTopicId(), questionDTO.getQuestion(), questionDTO.getAnswer())) {
            return ResponseEntity.ok("Question saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/get/mainTopics")
    public ResponseEntity<List<Topic>> getAllMainTopics() {
        List<Topic> mainTopics = topicService.findTopicsWithoutQuestions();
        return ResponseEntity.ok(mainTopics);
    }

    @GetMapping("/get/subTopics")
    public ResponseEntity<List<Topic>> getAllSubTopics() {
        List<Topic> subTopics = topicService.getALlSubTopics();
        return ResponseEntity.ok(subTopics);
    }
}

