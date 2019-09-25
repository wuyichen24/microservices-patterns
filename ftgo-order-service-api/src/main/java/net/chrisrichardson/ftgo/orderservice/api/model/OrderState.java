package net.chrisrichardson.ftgo.orderservice.api.model;

public enum OrderState {
  APPROVAL_PENDING,
  APPROVED,
  REJECTED,
  CANCEL_PENDING,
  CANCELLED,
  REVISION_PENDING,
}
