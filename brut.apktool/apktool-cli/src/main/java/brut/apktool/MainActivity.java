/*
 *  Copyright (C) 2010 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2010 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.apktool;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Main Android activity for Apktool.
 */
public class MainActivity extends Activity {
    private static final int REQUEST_PERMISSIONS = 1;
    
    private EditText commandInput;
    private TextView outputText;
    private Button executeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commandInput = findViewById(R.id.commandInput);
        outputText = findViewById(R.id.outputText);
        executeButton = findViewById(R.id.executeButton);

        executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeCommand();
            }
        });

        // Request permissions
        requestPermissions();
    }

    private void requestPermissions() {
        String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        boolean allGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) 
                    != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, 
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (!allGranted) {
                Toast.makeText(this, "Permissions required for Apktool to function", 
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void executeCommand() {
        String command = commandInput.getText().toString().trim();
        if (command.isEmpty()) {
            Toast.makeText(this, "Please enter a command", Toast.LENGTH_SHORT).show();
            return;
        }

        // Redirect System.out and System.err to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(ps);
        System.setErr(ps);

        try {
            // Parse command into arguments
            String[] args = command.split("\\s+");
            
            // Execute apktool main
            Main.main(args);
            
            String output = baos.toString();
            outputText.setText(output);
        } catch (Exception e) {
            outputText.setText("Error: " + e.getMessage() + "\n" + baos.toString());
        } finally {
            // Restore System.out and System.err
            System.setOut(oldOut);
            System.setErr(oldErr);
        }
    }
}
