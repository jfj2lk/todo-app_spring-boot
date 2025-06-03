package app.controller;

import org.springframework.web.bind.annotation.RestController;

import app.model.Label;
import app.service.LabelService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LabelController {
  private final LabelService labelService;

  // 全てのLabel取得
  @GetMapping("/labels")
  public ResponseEntity<Map<String, Iterable<Label>>> getAllLabels() {
    Iterable<Label> allLabels = labelService.getAllLabels();
    return ResponseEntity.ok().body(Map.of("data", allLabels));
  }

}
