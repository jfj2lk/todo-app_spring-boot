package app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.form.label.CreateLabelForm;
import app.form.label.UpdateLabelForm;
import app.model.Label;
import app.service.LabelService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    /**
     * Label取得。
     */
    @GetMapping("/labels/{id}")
    public ResponseEntity<Map<String, Label>> get(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long labelId) {
        Label label = labelService.get(labelId, Long.valueOf(userId));
        return ResponseEntity.ok().body(Map.of("data", label));
    }

    /**
     * 全てのLabel取得。
     */
    @GetMapping("/labels")
    public ResponseEntity<Map<String, List<Label>>> getAll(
            @AuthenticationPrincipal String userId) {
        List<Label> labels = labelService.getAll(Long.valueOf(userId));
        return ResponseEntity.ok().body(Map.of("data", labels));
    }

    /**
     * Label作成。
     */
    @PostMapping("/labels")
    public ResponseEntity<Map<String, Label>> create(
            @AuthenticationPrincipal String userId,
            @RequestBody @Validated CreateLabelForm createLabelForm) {
        Label createdLabel = labelService.create(Long.valueOf(userId), createLabelForm);
        return ResponseEntity.ok().body(Map.of("data", createdLabel));
    }

    /**
     * Label更新。
     */
    @PatchMapping("/labels/{id}")
    public ResponseEntity<Map<String, Label>> update(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long labelId,
            @RequestBody @Validated UpdateLabelForm UpdateLabelForm) {
        Label updatedLabel = labelService.update(labelId, Long.valueOf(userId), UpdateLabelForm);
        return ResponseEntity.ok().body(Map.of("data", updatedLabel));
    }

    /**
     * Label削除。
     */
    @DeleteMapping("/labels/{id}")
    public ResponseEntity<Map<String, Long>> delete(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long labelId) {
        Long deletedLabelId = labelService.delete(labelId, Long.valueOf(userId));
        return ResponseEntity.ok().body(Map.of("data", deletedLabelId));
    }
}
