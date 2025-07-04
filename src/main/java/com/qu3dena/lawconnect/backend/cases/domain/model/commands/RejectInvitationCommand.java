package com.qu3dena.lawconnect.backend.cases.domain.model.commands;

import java.util.UUID;

/**
 * Command to reject an invitation to a case.
 *
 * @param invitationId the ID of the invitation being rejected
 * @param lawyerId the ID of the lawyer rejecting the invitation
 * @since 1.0
 * @author GonzaloQu3dena
 */
public record RejectInvitationCommand(Long invitationId, UUID lawyerId) {
}