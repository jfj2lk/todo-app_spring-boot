package app.controller;

import org.springframework.web.bind.annotation.RestController;

import app.form.label.AddLabelForm;
import app.form.label.UpdateLabelForm;
import app.model.Label;
import app.service.LabelService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.h2.command.dml.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  // Label追加
  @PostMapping("/labels")
  public ResponseEntity<Map<String, Label>> addLabel(@RequestBody @Validated AddLabelForm addLabelForm) {
    Label addedLabel = labelService.addLabel(addLabelForm);
    return ResponseEntity.ok().body(Map.of("data", addedLabel));
  }

  // Label更新
  @PatchMapping("/labels/{id}")
  public ResponseEntity<Map<String, Label>> updateLabel(
      @PathVariable("id") Long labelId,
      @RequestBody @Validated UpdateLabelForm UpdateLabelForm) {
    Label updatedLabel = labelService.updateLabel(labelId, UpdateLabelForm);
    return ResponseEntity.ok().body(Map.of("data", updatedLabel));
  }
}
