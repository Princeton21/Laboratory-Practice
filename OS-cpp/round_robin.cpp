#include<bits/stdc++.h>
using namespace std;
int main() {
	int n, i, qt, count = 0 , temp, sq = 0;
	double awt = 0, atat = 0;
	vector<int> bt(10), wt(10), tat(10), rem_bt(10);

	cout << "Enter the number of process max 10 : \n";
	cin >> n;
	cout << "Enter the burst time of the process\n";
	for (i = 0 ; i < n; ++i) {
		cout << "P" << i << " : ";
		cin >> bt[i];
		rem_bt[i] = bt[i];
	}
	cout << "Enter the quantum time : ";
	cin >> qt;

	while (true) {
		for (i = 0, count = 0; i < n; i++) {
			temp = qt;
			if (rem_bt[i] == 0) {
				count++;
				continue;
			}
			if (rem_bt[i] > qt)
				rem_bt[i] = rem_bt[i] - qt;
			else if (rem_bt[i] >= 0) {
				temp = rem_bt[i];
				rem_bt[i] = 0;
			}
			sq = sq + temp;
			tat[i] = sq;
		}
		if (n == count)
			break;
	}
	cout << "----------------------------------------------\n";
	cout << "\nProcess\t      Burst Time\t       Turnaround Time\t          Waiting Time\n";
	cout << "----------------------------------------------\n";

	for (i = 0; i < n; ++i) {
		wt[i] = tat[i] - bt[i];
		awt += wt[i];
		atat += tat[i];
		cout << "\n";
		cout << i + 1 << " \t" << bt[i] << " \t \t" << tat[i] << " \t\t " << wt[i] << "\n";
	}
	awt /= n;
	atat /= n;

	cout << "\nAverage wait time : " <<  awt <<  endl;
	cout << "\nAverage turnaround time : " << atat << endl;



}