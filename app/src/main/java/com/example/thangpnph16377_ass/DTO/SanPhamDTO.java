package com.example.thangpnph16377_ass.DTO;

public class SanPhamDTO {
     String macv, name, content, status, start, ends;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String macv, String name, String content, String status, String start, String ends) {
        this.macv = macv;
        this.name = name;
        this.content = content;
        this.status = status;
        this.start = start;
        this.ends = ends;
    }

    public SanPhamDTO(String name, String content, String status, String start, String ends) {
        this.name = name;
        this.content = content;
        this.status = status;
        this.start = start;
        this.ends = ends;
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }
}
