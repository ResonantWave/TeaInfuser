/* Tea Preparer
Copyright (C) 2016 ResonantWave
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.ionicbyte.teapreparer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class Main extends Activity {

    NumberPicker np;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        np = (NumberPicker) findViewById(R.id.numberPicker1);
        np.setMinValue(1);
        np.setMaxValue(10);
        np.setWrapSelectorWheel(false);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                np.setEnabled(false);
                textView.setVisibility(View.VISIBLE);

                try { // Send request to the Raspberry Pi
                    new URL("http://192.168.0.4:8000?time=" + String.valueOf(np.getValue() * 60)).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
                animation.setDuration(np.getValue() * 1000 * 60 * 2);
                animation.setInterpolator(new LinearInterpolator());
                animation.start();

                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Your tea is ready! :)");
                        np.setEnabled(true);
                        button.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

    }
}
