package com.nithin.finalproject.Dialogues;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nithin.finalproject.R;

import static android.text.TextUtils.isEmpty;

/**
 * Created by nithin on 3/19/2018.
 */

public class Resendverification extends DialogFragment {

    private EditText resendemail,resendpassword;
    private TextView cancel,resendconfirm;
    private Context mcontext;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resendverification, container, false);
        mcontext = getActivity();
        resendemail = (EditText) view.findViewById(R.id.resendemail);
        resendpassword = (EditText) view.findViewById(R.id.resendpassword);
        cancel = (TextView) view.findViewById(R.id.cancel);
        resendconfirm = (TextView) view.findViewById(R.id.resendconfirm);

        resendconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isEmpty(resendemail.getText().toString())
                        && !isEmpty(resendpassword.getText().toString())){

                    //temporarily authenticate and resend verification email
                    authenticateAndResendEmail(resendemail.getText().toString(),
                            resendpassword.getText().toString());
                }else{
                    Toast.makeText(mcontext, "all fields must be filled out", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    private void authenticateAndResendEmail(String email,String password)
    {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendVerificationEmail();
                            FirebaseAuth.getInstance().signOut();
                            getDialog().dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext, "Invalid Credentials. \nReset your password and try again", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mcontext, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(mcontext, "couldn't send email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }



}
