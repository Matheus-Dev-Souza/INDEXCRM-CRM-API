package com.indexcrm.domain.marketing;

import com.indexcrm.domain.BaseEntity;
import com.indexcrm.domain.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "email_campaigns")
public class EmailCampaign extends BaseEntity {

    private String subject;
    
    @Column(columnDefinition = "TEXT")
    private String bodyHtml;
    
    private String status; // DRAFT, SENT, FAILED
    private int sentCount;

    @ManyToOne
    private User author;

    // --- GETTERS E SETTERS ---

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBodyHtml() { return bodyHtml; }
    public void setBodyHtml(String bodyHtml) { this.bodyHtml = bodyHtml; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getSentCount() { return sentCount; }
    public void setSentCount(int sentCount) { this.sentCount = sentCount; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
}