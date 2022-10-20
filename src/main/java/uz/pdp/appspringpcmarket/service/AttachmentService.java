package uz.pdp.appspringpcmarket.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appspringpcmarket.entity.Attachment;
import uz.pdp.appspringpcmarket.entity.AttachmentContent;
import uz.pdp.appspringpcmarket.repository.AttachmentContentRepository;
import uz.pdp.appspringpcmarket.repository.AttachmentRepository;

import java.util.Optional;

@Service
public class AttachmentService {
    final
    AttachmentRepository attachmentRepository;

    final
    AttachmentContentRepository attachmentContentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository,
                             AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    public Attachment add(MultipartFile photo){
        try  {
            Attachment attachment = new Attachment();
            attachment.setContentType(photo.getContentType());
            attachment.setName(photo.getOriginalFilename());
            attachment.setSize(photo.getSize());
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(savedAttachment);
            attachmentContent.setBytes(photo.getBytes());
            attachmentContentRepository.save(attachmentContent);
            return attachment;
        }catch (Exception e){
            return null;
        }
    }

    public ResponseEntity<?> get(Integer id){
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if(!byId.isPresent())
            return null;

        Attachment attachment = byId.get();
        Optional<AttachmentContent> byAttachment = attachmentContentRepository.findByAttachment(attachment);

        if(!byAttachment.isPresent())
            return null;

        AttachmentContent attachmentContent = byAttachment.get();

        return ResponseEntity.ok().contentType(MediaType.valueOf(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + attachment.getName() + "\"")
                .body(attachmentContent.getBytes());
    }

    public boolean delete(Attachment attachment){
        Optional<AttachmentContent> attachmentContentOptional = attachmentContentRepository.findByAttachment(attachment);
        if(!attachmentContentOptional.isPresent())
            return false;
        try{
            attachmentContentRepository.deleteById(attachmentContentOptional.get().getId());
            attachmentRepository.delete(attachment);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
